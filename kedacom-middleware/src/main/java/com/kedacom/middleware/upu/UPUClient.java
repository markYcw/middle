package com.kedacom.middleware.upu;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.upu.domain.UPU;
import com.kedacom.middleware.upu.domain.UPUMt;
import com.kedacom.middleware.upu.request.FindMtByE164Request;
import com.kedacom.middleware.upu.request.LoginRequest;
import com.kedacom.middleware.upu.request.LogoutRequest;
import com.kedacom.middleware.upu.request.UPURequest;
import com.kedacom.middleware.upu.response.FindMtByE164Response;
import com.kedacom.middleware.upu.response.LoginResponse;
import com.kedacom.middleware.upu.response.UPUResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “UPU服务器”接口访问。
 * 
 * @author LinChaoYu
 *
 */
public class UPUClient {
	private static final Logger log = LogManager.getLogger(UPUClient.class);
	private KM km;

	/**
	 * 会话管理。根据目前的设计，一个UPU最多一个会话，好比一个监控平台只有一个主链。
	 */
	
	private UPUSessionManager sessionManager;
	
	/**
	 * “UPU”信息集合。
	 * key：UPU标识， value:UPU详细信息
	 */
	private Hashtable<String, UPU> upuCacheByID = new Hashtable<String, UPU>();
	
	/**
	 * 服务器数据和状态监听器
	 */
	private List<UPUNotifyListener> listeners = new ArrayList<UPUNotifyListener>();
	
	/**
	 * UPU连接监控线程
	 */
	private UPUConnMonitorThread upuConnMonitorThread;

	
	public UPUClient(KM km){
		this.km = km;

		this.sessionManager = new UPUSessionManager(this);
		UPUClientListener listener = new UPUClientListener(this);
		
		/*
		 * TODO 如果当UPUClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中UPUClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		
	}

	/**
	 * 增加监听器
	 * @see #removeListener(UPUNotifyListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(UPUNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(UPUNotifyListener)
	 * @return
	 */
	public boolean removeListener(UPUNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	public List<UPUNotifyListener> getAllListeners(){
		List<UPUNotifyListener> list = new ArrayList<UPUNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public UPUSessionManager getSessionManager() {
		return sessionManager;
	}
	/**
	 * 增加UPU信息。
	 * @param UPU
	 */
	public void addUPU(UPU UPU){
		this.upuCacheByID.put(UPU.getId(), UPU);
		this.reStartConnect(UPU.getId());
	}
	
	/**
	 * 更新UPU信息
	 */
	public void updateUPU(UPU upu){
		UPU old = this.upuCacheByID.get(upu.getId());
		if( !(old.getIp().equals(upu.getIp()))
				|| old.getPort() != upu.getPort()
				){
			//需要重新连接
			old.update(upu);
			this.logout(upu.getId());
			this.reStartConnect(upu.getId());
		}else{
			old.update(upu);
			
			//解决在upuCacheByID中已经有该UPU的信息，但是UPU未连接，如果UPU信息不发生改变，那么UPU再也连接不上
			UPUSession session = sessionManager.getSessionByID(upu.getId());
			if(session == null || !session.isLogin()){//当前UPU未连接
				this.reStartConnect(upu.getId());
			}
		}
	}
	
	/**
	 * 删除UPU信息。
	 * @return
	 */
	private void removeUPU(UPU UPU){
		if(UPU == null){
			return;
		}
		String id = UPU.getId();
		
		if(upuConnMonitorThread != null){
			upuConnMonitorThread.removeUPUId(id);
		}
		
		UPUSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.upuCacheByID.remove(id);
	}
	
	/**
	 * 设置（添加或修改）UPU信息
	 * @param UPU
	 */
	public void setUPU(UPU UPU){
		com.kedacom.middleware.upu.domain.UPU old = this.upuCacheByID.get(UPU.getId());
		if(old != null){
			updateUPU(UPU);
		}else{
			addUPU(UPU);
		}
	}
	
	/**
	 * 删除UPU信息。
	 * @param id
	 * @see #addUPU(UPU)
	 * @see #updateUPU(UPU)
	 * @return
	 */
	public UPU removeUPUByID(String id){
		UPU UPU = upuCacheByID.get(id);
		this.removeUPU(UPU);
		return UPU;
	}

	private IClient getClient(){
		return km.getClient();
	}

	/**
	 * 停止
	 */
	public void stop(){
		this.stopReStartConnect();
		this.sessionManager.clear();
	}
	
	/**
	 * 获取会话标识
	 * @param id UPU的标识，{@link UPU#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByID(String id) throws ConnectException {
		UPUSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("UPU未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == UPUSessionStatus.CONNECTING){
				throw new ConnectException("UPU正在登录中");
			}else if(session.getStatus() == UPUSessionStatus.FAILED){
				throw new ConnectException("UPU登录失败");
			}else if(session.getStatus() == UPUSessionStatus.DISCONNECT){
				throw new ConnectException("UPU连接中断");
			}else{
				throw new ConnectException("UPU未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}
	
	/**
	 * 获取会话标识
	 * @param id UPU的标识，{@link UPU#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
		UPUSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("UPU未连接");
		}
		
		return session.getSsid();
	}
	
	public void onDisConnect(String id){
		try {
			int ssid = this.tryGetSSIDByID(id);
			sessionManager.removeSession(ssid);
		} catch (ConnectException e) {
			log.debug("onDisConnect: not login", e);
		}
		this.reStartConnect(id);
	}
	
	/**
	 * 在单独的线程中重新连接UPU
	 * @param id UPU的标识
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(upuConnMonitorThread == null || !upuConnMonitorThread.isWork()){
			upuConnMonitorThread = new UPUConnMonitorThread(this);
			upuConnMonitorThread.setTimeout(15000);
			upuConnMonitorThread.setName("UPU-ConnMonitor");
			upuConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		upuConnMonitorThread.addUPUId(id);
		if(newThread){
			upuConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(upuConnMonitorThread != null){
			upuConnMonitorThread.stop();
			upuConnMonitorThread = null;
		}
	}
	
	/**
	 * 返回指定的UPU是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		UPUSession session = sessionManager.getSessionByID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		UPUSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	private UPUResponse sendRequest(UPURequest request) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
		UPUResponse response = (UPUResponse)getClient().sendRequest(request);
		
		int err = response.getErrorcode();
		if(err > 0){
			//远端返回错误码
			RemoteException e = new RemoteException(String.valueOf(err));
			e.setErrorcode(err);
			throw e;
		}
		
		return response;
	}
	
	/**
	 * 登录
	 * @param ip UPU的IP
	 * @param port UPU的端口
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, int port) throws KMException{
		
		UPUSession session = sessionManager.getSessionByIP(ip);
		if(session != null){
			/*
			 * UPU已经登录过。
			 * 目前程序设计为，一个UPU只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		int ssid = this.login0(ip, port);
		if(ssid > 0){
			//登录成功
			session = new UPUSession();
			session.setSsid(ssid);
			sessionManager.putSession(session);
		}
		
		return ssid;
	}

	/**
	 * 根据ID登录UPU
	 * @see # id (String, String, String, String)
	 * @see #logout(String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException{
		UPU upu = upuCacheByID.get(id);
		if(upu == null){
			throw new DataException("UPU信息不存在,ID=" + id);
		}
		return this.loginByUPU(upu);
	}
	
	//最终实现登录的方法
	private int loginByUPU(UPU upu) throws KMException{
		
		String id = upu.getId();
		String ip = upu.getIp();
		int port = upu.getPort();
		
		UPUSession session = sessionManager.getSessionByID(id);
		if(session != null){
			//已登录
			return session.getSsid();
		}
		
		int ssid = this.login(ip, port);
		if(ssid > 0){
			//登录成功
			session = new UPUSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setStatus(UPUSessionStatus.CONNECTED);
			session.setUpu(upu);
			sessionManager.putSession(session);
			
		}
		
		return ssid;
	}

	private int login0(String ip, int port) throws KMException{
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setPort(port);
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 获取会话标识。
	 * @param UPUIP UPU的IP地址
	 * @return 如果UPU已连接，返回ssid，否则返回-1
	 */
	public int getSSIDByIp(String UPUIP){
		UPUSession session = sessionManager.getSessionByIP(UPUIP);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识。
	 * <pre>
	 *     如果UPU已经登录，直接返回会话标识;
	 *     如果UPU未登录，则先登录，然后返回会话标识。
	 * </pre>
	 * @param id UPU的标识，{@link UPU#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	private int tryLogin(String id) throws KMException{
		int ssid;
		UPUSession session = sessionManager.getSessionByID(id);
		if(session == null){
			//未登录
			ssid = this.login(id);
		}else{
			//已登录
			ssid = session.getSsid();
		}
		return ssid;
	}
	
	/**
	 * 登出UPU
	 * @param id
	 * @return
	 */
	public void logout(String id){
		int ssid = -1;
		try {
			ssid = this.tryGetSSIDByIDForLogout(id);
		} catch (ConnectException e) {
			log.debug("logout:not login", e);
		}
		
		if(ssid >= 0){
			UPUSession session = sessionManager.removeSession(ssid);
			if(session != null && session.isLogin()){
				LogoutRequest request = new LogoutRequest();
				request.setSsid(ssid);
				try {
					this.sendRequest(request);
				} catch (KMException e) {
					log.warn("logout faild", e);
				}
			}
		}
	}
	
	/**
	 * 根据终端标识获取终端信息
	 * @param id
	 * @param e164s
	 * @return
	 * @throws KMException 
	 */
	public List<UPUMt> findmtbye164(String id, List<String> e164s) throws KMException{
		int ssid = this.tryLogin(id);
		
		FindMtByE164Request request = new FindMtByE164Request();
		request.setSsid(ssid);
		
		request.setE164s(e164s);
		
		FindMtByE164Response response = (FindMtByE164Response)this.sendRequest(request);
		
		return response.getMts();
	}
}
