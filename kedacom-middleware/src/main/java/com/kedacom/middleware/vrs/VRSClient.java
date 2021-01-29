package com.kedacom.middleware.vrs;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.vrs.domain.VRS;
import com.kedacom.middleware.vrs.request.LoginRequest;
import com.kedacom.middleware.vrs.request.LogoutRequest;
import com.kedacom.middleware.vrs.request.VRSRequest;
import com.kedacom.middleware.vrs.request.VrsQueryRecRequest;
import com.kedacom.middleware.vrs.response.LoginResponse;
import com.kedacom.middleware.vrs.response.VRSResponse;
import com.kedacom.middleware.vrs.response.VrsQueryRecResponse;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “录播服务器”接口访问。
 * @author LinChaoYu
 *
 */
public class VRSClient {
	private static final Logger log = Logger.getLogger(VRSClient.class);
	private KM km;

	/**
	 * 会话管理。根据目前的设计，一个VRS最多一个会话，好比一个监控平台只有一个主链。
	 */
	
	private VRSSessionManager sessionManager;
	/**
	 * “VRS”信息集合。
	 * key：VRS标识， value:VRS详细信息
	 */
	private Hashtable<String, VRS> vrsCacheByID = new Hashtable<String, VRS>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<VRSNotifyListener> listeners = new ArrayList<VRSNotifyListener>();
	
	/**
	 * VRS连接监控线程
	 */
	private VRSConnMonitorThread vrsConnMonitorThread;

	
	public VRSClient(KM km){
		this.km = km;

		this.sessionManager = new VRSSessionManager(this);
		VRSClientListener listener = new VRSClientListener(this);
		
		/*
		 * TODO 如果当VRSClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中VRSClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		
	}

	/**
	 * 增加监听器
	 * @see #removeListener(VRSNotifyListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(VRSNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(VRSNotifyListener)
	 * @return
	 */
	public boolean removeListener(VRSNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	protected List<VRSNotifyListener> getAllListeners(){
		List<VRSNotifyListener> list = new ArrayList<VRSNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public VRSSessionManager getSessionManager() {
		return sessionManager;
	}
	/**
	 * 增加VRS信息。
	 * @param mt
	 */
	public void addVRS(VRS vrs){
		this.vrsCacheByID.put(vrs.getId(), vrs);
		this.reStartConnect(vrs.getId());
	}
	
	/**
	 * 更新VRS信息
	 */
	public void updateVRS(VRS vrs){
		VRS old = this.vrsCacheByID.get(vrs.getId());
		if( !(old.getIp().equals(vrs.getIp()))
				|| !(old.getVersion().equals(vrs.getVersion()))
				){
			//需要重新连接
			old.update(vrs);
			this.logout(vrs.getId());
			this.reStartConnect(vrs.getId());
		}else{
			old.update(vrs);
			
			//解决在vrsCacheByID中已经有该VRS的信息，但是VRS未连接，如果VRS信息不发生改变，那么VRS再也连接不上
			VRSSession session = sessionManager.getSessionByID(vrs.getId());
			if(session == null || !session.isLogin()){//当前VRS未连接
				this.reStartConnect(vrs.getId());
			}
		}
	}
	
	/**
	 * 删除VRS信息。
	 * @return
	 */
	private void removeVRS(VRS vrs){
		if(vrs == null){
			return;
		}
		String id = vrs.getId();
		
		if(vrsConnMonitorThread != null){
			vrsConnMonitorThread.removeVRSId(id);
		}
		
		VRSSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.vrsCacheByID.remove(id);
	}
	
	/**
	 * 设置（添加或修改）VRS信息
	 * @param vrs
	 */
	public void setVRS(VRS vrs){
		VRS old = this.vrsCacheByID.get(vrs.getId());
		if(old != null){
			updateVRS(vrs);
		}else{
			addVRS(vrs);
		}
	}
	
	/**
	 * 删除VRS信息。
	 * @param id
	 * @see #addVRS(VRS)
	 * @see #updateVRS(VRS)
	 * @return
	 */
	public VRS removeVRSByID(String id){
		VRS vrs = vrsCacheByID.get(id);
		this.removeVRS(vrs);
		return vrs;
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
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByID(String id) throws ConnectException {
		VRSSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("VRS未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == VRSSessionStatus.CONNECTING){
				throw new ConnectException("VRS正在登录中");
			}else if(session.getStatus() == VRSSessionStatus.FAILED){
				throw new ConnectException("VRS登录失败");
			}else if(session.getStatus() == VRSSessionStatus.DISCONNECT){
				throw new ConnectException("VRS连接中断");
			}else{
				throw new ConnectException("VRS未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}
	
	/**
	 * 获取会话标识
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
		VRSSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("VRS未连接");
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
	 * 在单独的线程中重新连接VRS
	 * @param id VRS的标识
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(vrsConnMonitorThread == null || !vrsConnMonitorThread.isWork()){
			vrsConnMonitorThread = new VRSConnMonitorThread(this);
			vrsConnMonitorThread.setTimeout(15000);
			vrsConnMonitorThread.setName("VRS-ConnMonitor");
			vrsConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		vrsConnMonitorThread.addVRSId(id);
		if(newThread){
			vrsConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(vrsConnMonitorThread != null){
			vrsConnMonitorThread.stop();
			vrsConnMonitorThread = null;
		}
	}
	
	/**
	 * 返回指定的VRS是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		VRSSession session = sessionManager.getSessionByID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		VRSSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	private VRSResponse sendRequest(VRSRequest request) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
		VRSResponse response = (VRSResponse)getClient().sendRequest(request);
		
		int err = response.getErrorcode();
		if(err > 0){
			//远端返回错误码
			RemoteException e = new RemoteException("错误码:" + err);
			e.setErrorcode(err);
			throw e;
		}
		
		return response;
	}
	/**
	 * 登录
	 * @param ip VRS的IP地址
	 * @param version 软件版本
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, String user, String pwd,String version) throws KMException{
		
		VRSSession session = sessionManager.getSessionByIP(ip);
		if(session != null){
			/*
			 * VRS已经登录过。
			 * 目前程序设计为，一个VRS只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		int ssid = this.login0(ip, user, pwd, version);
		if(ssid > 0){
			//登录成功
			session = new VRSSession();
			session.setSsid(ssid);
			sessionManager.putSession(session);
		}
		
		return ssid;
	}

	/**
	 * 根据ID登录VRS
	 * @see #login(String, String, String, String)
	 * @see #logout(String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException{
		VRS vrs = vrsCacheByID.get(id);
		if(vrs == null){
			throw new DataException("VRS信息不存在,ID=" + id);
		}
		return this.loginByVRS(vrs);
	}
	
	//最终实现登录的方法
	private int loginByVRS(VRS vrs) throws KMException{
		
		String id = vrs.getId();
		String ip = vrs.getIp();
		String user = vrs.getUsername();
		String pwd = vrs.getPassword();
		String version = vrs.getVersion();
		
		VRSSession session = sessionManager.getSessionByID(id);
		if(session != null){
			//已登录
			return session.getSsid();
		}
		
		int ssid = this.login(ip, user, pwd, version);
		if(ssid > 0){
			//登录成功
			session = new VRSSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setStatus(VRSSessionStatus.CONNECTED);
			session.setVrs(vrs);
			sessionManager.putSession(session);
			
		}
		
		return ssid;
	}

	private int login0(String ip, String user, String pwd, String version) throws KMException{
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setUser(user);
		request.setPwd(pwd);
		
		if(VRS.VRS_VERSION_5_1.equals(version))
			request.setDevtype(DeviceType.VRS51.getValue());
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 获取会话标识。
	 * @param vrsIP vrs的IP地址
	 * @return 如果VRS已连接，返回ssid，否则返回-1
	 */
	public int getSSIDByIp(String vrsIP){
		VRSSession session = sessionManager.getSessionByIP(vrsIP);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识。
	 * <pre>
	 *     如果VRS已经登录，直接返回会话标识;
	 *     如果VRS未登录，则先登录，然后返回会话标识。
	 * </pre>
	 * @param id VRS的标识，{@link VRS#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	private int tryLogin(String id) throws KMException{
		int ssid;
		VRSSession session = sessionManager.getSessionByID(id);
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
	 * 登出VRS
	 * @param ssid
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
			VRSSession session = sessionManager.removeSession(ssid);
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
	 * 分页查询录像列表
	 * @param id
	 * @param pagenum
	 * @param pagesize
	 * @param recname
	 * @return
	 * @throws KMException
	 */
	public VrsQueryRecResponse queryRecList(String id, int pagenum, int pagesize, String recname) throws KMException{
		int ssid = this.tryLogin(id);
		
		VrsQueryRecRequest request = new VrsQueryRecRequest();
		request.setSsid(ssid);
		request.setPagenum(pagenum);
		request.setPagesize(pagesize);
		request.setIncludename(recname);
		
		VrsQueryRecResponse response = (VrsQueryRecResponse)this.sendRequest(request);
		
		return response;
	}
}
