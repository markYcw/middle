package com.kedacom.middleware.cu;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListener;
import com.kedacom.middleware.cu.devicecache.CuDeviceLoadThread;
import com.kedacom.middleware.cu.domain.Cu;
import com.kedacom.middleware.cu.request.CuRequest;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.LoginResponse;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “监控平台”接口访问。
 * @author TaoPeng
 * @see #getCuOperate()
 * @see #getSessionManager()
 *
 */
public class CuClient {

	private static final Logger log = Logger.getLogger(CuClient.class);

	/**
	 * 会话管理。根据目前的设计，一个终端最多一个会话，好比一个监控平台只有一个主链。
	 */
	private CuSessionManager sessionManager;
	
	/**
	 * “会话平台”集合。
	 * key：监控平台标识， value:监控平台详细信息
	 */
	private Hashtable<Integer, Cu> cuCacheByID = new Hashtable<Integer, Cu>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<CuNotifyListener> listeners = new ArrayList<CuNotifyListener>();

	/**
	 * Cu连接监控线程
	 */
	private CuConnMonitorThread cuConnMonitorThread;
	
	/**
	 * Cu设备加载线程
	 */
	private CuDeviceLoadThread deviceLoadThread;
	
	private CuOperate cuOperate;
	
	private KM km;
	public CuClient(KM km){
		
		this.km = km;
		this.sessionManager = new CuSessionManager(this);
		this.cuOperate = new CuOperate(this);

		deviceLoadThread = new CuDeviceLoadThread(this);
		CuClientListener listener = new CuClientListener(this);
		
		/*
		 * TODO 如果当CuClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中CuClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		tcpClient.addListener(deviceLoadThread);
	}
	
	private IClient getClient(){
		return km.getClient();
	}
	
	/**
	 * 获取监控平台的操作模块。通过此模块调用监控平台的功能；
	 * @return
	 */
	public CuOperate getCuOperate() {
		return cuOperate;
	}

	/**
	 * 停止
	 */
	public void stop(){
		this.stopReStartConnect();
	}
	
	/**
	 * 增加监听器
	 * @see #removeListener(TCPClientListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(CuNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(TCPClientListener)
	 * @return
	 */
	public boolean removeListener(CuNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	public List<CuNotifyListener> getAllListeners(){
		List<CuNotifyListener> list = new ArrayList<CuNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public CuSessionManager getSessionManager() {
		return sessionManager;
	}
	
	/**
	 * 增加监控平台信息。
	 * @see #addCu(Cu)
	 * @see #updateCu(Cu)
	 * @see #addOrUpdateCu(Cu)
	 * @see #removeCu(Cu)
	 * @see #removeCuByID(int)
	 * @param cu
	 */
	public void addCu(Cu cu){
		
		this.checkCu(cu);
		
		this.cuCacheByID.put(cu.getId(), cu);
		this.reStartConnect(cu.getId());
	}
	
	private boolean checkCu(Cu cu){
		if(cu.getId() <= 0){
			throw new RuntimeException("必须监控平台ID");
		}
		if(cu.getIp() == null || cu.getIp().trim().length() <= 0){
			throw new RuntimeException("必须指定监控平台IP");
		}
		return true;
	}
	
	/**
	 * 更新监控平台信息
	 * @param cu
	 * @see #addCu(Cu)
	 * @see #updateCu(Cu)
	 * @see #addOrUpdateCu(Cu)
	 * @see #removeCu(Cu)
	 * @see #removeCuByID(int)
	 */
	public void updateCu(Cu cu){

		this.checkCu(cu);
		
		Cu old = this.cuCacheByID.get(cu.getId());
		if( !(old.getIp().equals(cu.getIp()))
				|| !(old.getUsername().equals(cu.getUsername()))
				|| !(old.getPassword().equals(cu.getPassword()))
				|| !(old.getVersion()!= cu.getVersion())
				){
			//需要重新连接
			old.update(cu);
			this.reStartConnect(cu.getId());
		}else{
			old.update(cu);
		}
	}
	
	/**
	 * 添加或修改监控平台信息
	 * @param cu
	 * @see #addCu(Cu)
	 * @see #updateCu(Cu)
	 * @see #addOrUpdateCu(Cu)
	 * @see #removeCu(Cu)
	 * @see #removeCuByID(int)
	 */
	public void addOrUpdateCu(Cu cu){
		
		this.checkCu(cu);
		
		Cu old = this.cuCacheByID.get(cu.getId());
		if(old != null){
			updateCu(cu);
		}else{
			addCu(cu);
		}
	}
	
	/**
	 * 删除会话平台信息。
	 * @param cu
	 * @see #addCu(Cu)
	 * @see #updateCu(Cu)
	 * @see #addOrUpdateCu(Cu)
	 * @see #removeCu(Cu)
	 * @see #removeCuByID(int)
	 */
	private void removeCu(Cu cu){
		if(cu == null){
			return;
		}
		int id = cu.getId();
		
		if(cuConnMonitorThread != null){
			cuConnMonitorThread.removeMcuId(id);
		}
		
		CuSession session = sessionManager.getSessionByCuID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.cuCacheByID.remove(id);
	}
	
	/**
	 * 删除会话平台信息。
	 * @param id
	 * @see #addCu(Cu)
	 * @see #updateCu(Cu)
	 * @see #addOrUpdateCu(Cu)
	 * @see #removeCu(Cu)
	 * @see #removeCuByID(int)
	 * @return
	 */
	public Cu removeCuByID(int id){
		Cu Cu = cuCacheByID.get(id);
		this.removeCu(Cu);
		return Cu;
	}

	/**
	 * 获取指定的平台信息。
	 * @param id 平台ID
	 * @return
	 */
	public Cu getCuByID(int id){
		return this.cuCacheByID.get(id);
	}
	
	/**
	 * 获取会话标识
	 * @param id 监控平台ID，{@link Cu#getId()}
	 * @return 如果已连接，返回ssid，否则返回{@link CuSession.INVALID_SSID}
	 */
	protected int tryGetSSIDByID(int id) throws ConnectException {
		CuSession session = sessionManager.getSessionByCuID(id);
		if(session == null){
			throw new ConnectException("监控平台未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == CuSessionStatus.CONNECTING){
				throw new ConnectException("监控平台正在登录中");
			}else if(session.getStatus() == CuSessionStatus.FAILED){
				throw new ConnectException("监控平台登录失败");
			}else if(session.getStatus() == CuSessionStatus.disconnect){
				throw new ConnectException("监控平台连接中断");
			}else{
				throw new ConnectException("监控平台未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}

	private int getSSIDByID(int id){
		return sessionManager.getSSIDByCuID(id);
	}
	
	/**
	 * 根据ID登录监控平台
	 * @see #addCu(Cu)
	 * @see #login(String, String, String, String)
	 * @see #logout(int)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(int id) throws KMException{
		Cu cu = cuCacheByID.get(id);
		if(cu == null){
			throw new DataException("监控平台信息不存在,ID=" + id);
		}
		return this.loginByCu(cu);
	}
	
	//最终实现登录的方法
	@SuppressWarnings("deprecation")
	public int loginByCu(Cu cu) throws KMException{
		
		int id = cu.getId();
		String ip = cu.getIp();
		String user = cu.getUsername();
		String pwd = cu.getPassword();
		
		CuSession session = sessionManager.getSessionByCuID(id);
		if(session != null){
			/*
			 * “监控平台”已经登录。
			 * 目前程序设计为，一个监控平台只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			if(session.isLogin()){
				return session.getSsid();
			}else{
				//注销无效的会话
				this.logout(id); //此处注销登录，在中间件上可能已经不存在此ssid，如果中间件直接忽略并返回响应。
			}
		}
		
		LoginResponse response = this.cuOperate.login(cu.getVersion(), ip, cu.getPort(), user, pwd);
		int ssid = response.getSsid();
		if(ssid != CuSession.INVALID_SSID){
			//登录成功
			session = new CuSession();
			session.setSsid(ssid);
			session.setCu(cu);
			
			session.setUserno(response.getUserno());
			session.setCmuno(response.getCmuno());
			session.setNatIp(response.getNatIp());
			session.setNatPort(response.getNatPort());
			
			session.setStatus(CuSessionStatus.CONNECTED);
			session.setLastTime(System.currentTimeMillis());
			sessionManager.putSession(session);
			
			//开始加载分组
			deviceLoadThread.addSsid(ssid);
			
		}
		
		log.debug("已登录CU：ip=" + ip + "name=" + cu.getName());
		return ssid;
	}
	

	/**
	 * 返回指定的监控平台是否已经登录
	 * @param id 监控平台ID
	 * @return
	 */
	public boolean isLogin(int id){
		CuSession session = sessionManager.getSessionByCuID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		CuSession session = sessionManager.getSessionBySSID(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	/**
	 * 拼接一串字符串
	 * @param strings
	 * @return
	 */
	protected static String concat(Object ...strings){
		StringBuffer sb = new StringBuffer();
		for(Object s : strings){
			sb.append(s);
		}
		return sb.toString();
	}

	protected CuResponse sendRequest(CuRequest request) throws KMException{
		return this.sendRequest(request, 0);//超时时间设置为0表示无效，底层会使用默认的超时时间
	}
	protected CuResponse sendRequest(CuRequest request, long timeout) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);

		CuResponse response = (CuResponse)getClient().sendRequest(request, timeout);
		
		int errCode = response.getErrorcode();
		if(errCode > 0){
			//远端返回错误码
			String msg = concat("错误：", errCode, " : ", CuErrorCode.getMessage(errCode));
			RemoteException e = new RemoteException(msg);
			e.setErrorcode(errCode);
			throw e;
		}
		
		return response;
	}

	/**
	 * 登出监控平台。<br/>
	 * 一般而言，用户不需要调用此方法，因为一旦登出监控平台，可能会影响到其他使用此监控平台的用户。
	 * @param cuId
	 * @return
	 * @see #login(int)
	 */
	protected void logout(int cuId){
		int ssid = this.getSSIDByID(cuId);
		if(ssid >= 0){
			deviceLoadThread.removeSsid(ssid);
			CuSession session = sessionManager.removeSession(ssid);
			if(session != null && session.isLogin()){
				try {
					this.cuOperate.logout(ssid);
				} catch (KMException e) {
					log.warn("logout faild", e);
				}
				//session.getDeviceCache().clear(); //暂不确定：注销后是否清楚会话中的设备
			}
		}
	}
	
	protected void onDisConnect(int id){
		try {
			int ssid = this.getSSIDByID(id);
			sessionManager.removeSession(ssid);
		} catch (Exception e) {
			log.debug("onDisConnect: not login", e);
		}
		this.reStartConnect(id);
	}
	
	/**
	 * 在单独的线程中重新连接监控平台
	 * @param id 监控平台ID
	 */
	public void reStartConnect(int id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(cuConnMonitorThread == null || !cuConnMonitorThread.isWork()){
			cuConnMonitorThread = new CuConnMonitorThread(this);
			cuConnMonitorThread.setTimeout(15000);
			cuConnMonitorThread.setName("Cu-ConnMonitor");
			cuConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		cuConnMonitorThread.addMcuId(id);
		if(newThread){
			cuConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(cuConnMonitorThread != null){
			cuConnMonitorThread.stop();
			cuConnMonitorThread = null;
		}
	}
	
	
}
