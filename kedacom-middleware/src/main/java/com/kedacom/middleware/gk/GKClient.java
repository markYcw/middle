package com.kedacom.middleware.gk;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.gk.domain.GK;
import com.kedacom.middleware.gk.domain.Point;
import com.kedacom.middleware.gk.request.GKRequest;
import com.kedacom.middleware.gk.request.GetRegentityListRequest;
import com.kedacom.middleware.gk.request.LoginRequest;
import com.kedacom.middleware.gk.request.LogoutRequest;
import com.kedacom.middleware.gk.response.GKResponse;
import com.kedacom.middleware.gk.response.GetRegentityListResponse;
import com.kedacom.middleware.gk.response.LoginResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “GK服务器”接口访问。
 * @author LinChaoYu
 *
 */
public class GKClient {
	private static final Logger log = LogManager.getLogger(GKClient.class);
	private KM km;

	/**
	 * 会话管理。根据目前的设计，一个GK最多一个会话，好比一个监控平台只有一个主链。
	 */
	
	private GKSessionManager sessionManager;
	/**
	 * “GK”信息集合。
	 * key：GK标识， value:GK详细信息
	 */
	private Hashtable<String, GK> gkCacheByID = new Hashtable<String, GK>();
	/**
	 * 服务器数据和状态监听器
	 */
	private List<GKNotifyListener> listeners = new ArrayList<GKNotifyListener>();
	
	/**
	 * GK连接监控线程
	 */
	private GKConnMonitorThread gkConnMonitorThread;

	
	public GKClient(KM km){
		this.km = km;

		this.sessionManager = new GKSessionManager(this);
		GKClientListener listener = new GKClientListener(this);
		
		/*
		 * TODO 如果当GKClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
		 * 但由于实际应用中GKClient只有一个，所以此处暂不处理
		 */
		TCPClient tcpClient = (TCPClient)km.getClient();
		tcpClient.addListener(listener);
		
	}

	/**
	 * 增加监听器
	 * @see #removeListener(GKNotifyListener)
	 * @param listener
	 * @return
	 */
	public boolean addListener(GKNotifyListener listener){
		return this.listeners.add(listener);
	}
	
	/**
	 * 删除监听器
	 * @param listener
	 * @see #addListener(GKNotifyListener)
	 * @return
	 */
	public boolean removeListener(GKNotifyListener listener){
		return this.listeners.remove(listener);
	}
	
	protected List<GKNotifyListener> getAllListeners(){
		List<GKNotifyListener> list = new ArrayList<GKNotifyListener>(this.listeners.size());
		list.addAll(listeners);
		return list;
	}
	
	/**
	 * 获取会话管理器
	 * @return
	 */
	public GKSessionManager getSessionManager() {
		return sessionManager;
	}
	/**
	 * 增加GK信息。
	 * @param gk
	 */
	public void addGK(GK gk){
		this.gkCacheByID.put(gk.getId(), gk);
		this.reStartConnect(gk.getId());
	}
	
	/**
	 * 更新GK信息
	 */
	public void updateGK(GK gk){
		GK old = this.gkCacheByID.get(gk.getId());
		if( !(old.getIp().equals(gk.getIp()))
				|| !(old.getUsername().equals(gk.getUsername()))
				|| !(old.getPassword().equals(gk.getPassword()))
				|| old.getPort() != gk.getPort()
				){
			//需要重新连接
			old.update(gk);
			this.logout(gk.getId());
			this.reStartConnect(gk.getId());
		}else{
			old.update(gk);
			
			//解决在gkCacheByID中已经有该GK的信息，但是GK未连接，如果GK信息不发生改变，那么GK再也连接不上
			GKSession session = sessionManager.getSessionByID(gk.getId());
			if(session == null || !session.isLogin()){//当前GK未连接
				this.reStartConnect(gk.getId());
			}
		}
	}
	
	/**
	 * 删除GK信息。
	 * @return
	 */
	private void removeGK(GK gk){
		if(gk == null){
			return;
		}
		String id = gk.getId();
		
		if(gkConnMonitorThread != null){
			gkConnMonitorThread.removeGKId(id);
		}
		
		GKSession session = sessionManager.getSessionByID(id);
		if(session != null){
			this.logout(id);
		}
		
		this.gkCacheByID.remove(id);
	}
	
	/**
	 * 设置（添加或修改）GK信息
	 * @param gk
	 */
	public void setGK(GK gk){
		GK old = this.gkCacheByID.get(gk.getId());
		if(old != null){
			updateGK(gk);
		}else{
			addGK(gk);
		}
	}
	
	/**
	 * 删除GK信息。
	 * @param id
	 * @see #addGK(GK)
	 * @see #updateGK(GK)
	 * @return
	 */
	public GK removeGKByID(String id){
		GK gk = gkCacheByID.get(id);
		this.removeGK(gk);
		return gk;
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
	 * @param id GK的标识，{@link GK#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByID(String id) throws ConnectException{
		GKSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("GK未连接");
		}
		if(!session.isLogin()){
			if(session.getStatus() == GKSessionStatus.CONNECTING){
				throw new ConnectException("GK正在登录中");
			}else if(session.getStatus() == GKSessionStatus.FAILED){
				throw new ConnectException("GK登录失败");
			}else if(session.getStatus() == GKSessionStatus.DISCONNECT){
				throw new ConnectException("GK连接中断");
			}else{
				throw new ConnectException("GK未登录");
			}
		}
		int ssid = session.getSsid();
		return ssid;
	}
	
	/**
	 * 获取会话标识
	 * @param id GK的标识，{@link GK#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 */
	private int tryGetSSIDByIDForLogout(String id) throws ConnectException{
		GKSession session = sessionManager.getSessionByID(id);
		if(session == null){
			throw new ConnectException("GK未连接");
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
	 * 在单独的线程中重新连接GK
	 * @param id GK的标识
	 */
	public void reStartConnect(String id){
		
		if(this.isLogin(id)){
			this.logout(id);
		}
		
		boolean newThread = false;
		if(gkConnMonitorThread == null || !gkConnMonitorThread.isWork()){
			gkConnMonitorThread = new GKConnMonitorThread(this);
			gkConnMonitorThread.setTimeout(15000);
			gkConnMonitorThread.setName("GK-ConnMonitor");
			gkConnMonitorThread.setDaemon(true);
			newThread = true;
		}
		gkConnMonitorThread.addGKId(id);
		if(newThread){
			gkConnMonitorThread.start();
		}
	}
	
	private void stopReStartConnect(){
		if(gkConnMonitorThread != null){
			gkConnMonitorThread.stop();
			gkConnMonitorThread = null;
		}
	}
	
	/**
	 * 返回指定的GK是否已经登录
	 * @param id
	 * @return
	 */
	public boolean isLogin(String id){
		GKSession session = sessionManager.getSessionByID(id);
		boolean login = session != null && session.isLogin();
		return login;
	}
	
	private void refreshTime(int ssid){
		GKSession session = sessionManager.getSessionBySsid(ssid);
		if(session != null){
			session.refreshTime();
		}
	}
	private GKResponse sendRequest(GKRequest request) throws KMException{
		
		int ssid = request.getSsid();
		this.refreshTime(ssid);
		
		GKResponse response = (GKResponse)getClient().sendRequest(request);
		
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
	 * @param ip GK的IP
	 * @param port GK的端口
	 * @param user 用户名
	 * @param pwd 密码
	 * @param isadmin 密码
	 * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
	 * @see #login(String)
	 * @see #logout(String)
	 * @throws KMException 
	 */
	public int login(String ip, int port, String user, String pwd, int isadmin) throws KMException{
		
		GKSession session = sessionManager.getSessionByIP(ip);
		if(session != null){
			/*
			 * GK已经登录过。
			 * 目前程序设计为，一个GK只需要登录一次，类似于在kplatform中只登录一个主链。
			 */
			return session.getSsid();
		}
		
		int ssid = this.login0(ip, port, user, pwd, isadmin);
		if(ssid > 0){
			//登录成功
			session = new GKSession();
			session.setSsid(ssid);
			sessionManager.putSession(session);
		}
		
		return ssid;
	}

	/**
	 * 根据ID登录GK
	 * @see #login(String, String, String, String)
	 * @see #logout(String)
	 * @param id
	 * @return
	 * @throws KMException
	 */
	public int login(String id) throws KMException{
		GK gk = gkCacheByID.get(id);
		if(gk == null){
			throw new DataException("GK信息不存在,ID=" + id);
		}
		return this.loginByGK(gk);
	}
	
	//最终实现登录的方法
	private int loginByGK(GK gk) throws KMException{
		
		String id = gk.getId();
		String ip = gk.getIp();
		int port = gk.getPort();
		String user = gk.getUsername();
		String pwd = gk.getPassword();
		int isadmin = gk.getIsadmin();
		
		GKSession session = sessionManager.getSessionByID(id);
		if(session != null){
			//已登录
			return session.getSsid();
		}
		
		int ssid = this.login(ip, port, user, pwd, isadmin);
		if(ssid > 0){
			//登录成功
			session = new GKSession();
			session.setSsid(ssid);
			session.setLastTime(System.currentTimeMillis());
			session.setStatus(GKSessionStatus.CONNECTED);
			session.setGk(gk);
			sessionManager.putSession(session);
			
		}
		
		return ssid;
	}

	private int login0(String ip, int port, String username, String password, int isadmin) throws KMException{
		LoginRequest request = new LoginRequest();
		request.setIp(ip);
		request.setPort(port);
		request.setUser(username);
		request.setPwd(password);
		request.setIsadmin(isadmin);
		
		LoginResponse response = (LoginResponse)this.sendRequest(request);
		int ssid = response.getSsid();
		return ssid;
	}

	/**
	 * 获取会话标识。
	 * @param gkIP GK的IP地址
	 * @return 如果GK已连接，返回ssid，否则返回-1
	 */
	public int getSSIDByIp(String gkIP){
		GKSession session = sessionManager.getSessionByIP(gkIP);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识。
	 * <pre>
	 *     如果GK已经登录，直接返回会话标识;
	 *     如果GK未登录，则先登录，然后返回会话标识。
	 * </pre>
	 * @param id GK的标识，{@link GK#getId()}
	 * @return 如果已连接，返回ssid，否则返回-1
	 * @throws KMException 
	 */
	private int tryLogin(String id) throws KMException{
		int ssid;
		GKSession session = sessionManager.getSessionByID(id);
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
	 * 登出GK
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
			GKSession session = sessionManager.removeSession(ssid);
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
	 * 获取已注册GK实体列表
	 * @param ssid
	 * @return
	 * @throws KMException 
	 */
	public List<Point> getRegentityList(String id) throws KMException{
		int ssid = this.tryLogin(id);
		GetRegentityListRequest request = new GetRegentityListRequest();
		request.setSsid(ssid);
		GetRegentityListResponse response = (GetRegentityListResponse)this.sendRequest(request);
		
		return response.getPoints();
	}
}
