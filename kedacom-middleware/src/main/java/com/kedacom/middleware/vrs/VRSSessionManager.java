package com.kedacom.middleware.vrs;

import com.kedacom.middleware.vrs.domain.VRS;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 录播服务器 会话管理
 * @author LinChaoYu
 *
 */
public class VRSSessionManager {

	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, VRSSession> sessions = new Hashtable<Integer, VRSSession>();

	private VRSClient vrsClient;
	private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
	private VRSSessionTimeoutManager timeoutManager;

	public VRSSessionManager(VRSClient vrsClient) {
		this.vrsClient = vrsClient;
	}
	
	public VRSClient getVrsClient() {
		return vrsClient;
	}

	public boolean isSessionTimeoutEnable() {
		return sessionTimeoutEnable;
	}

	public void setSessionTimeoutEnable(boolean sessionTimeoutEnable) {
		this.sessionTimeoutEnable = sessionTimeoutEnable;
	}

	private void startTimeoutManager(){
		if(sessionTimeoutEnable){
			if(timeoutManager == null){
				timeoutManager = new VRSSessionTimeoutManager(this);
				timeoutManager.setDaemon(true);
				timeoutManager.setName("VRS-SessionManager");
				timeoutManager.setTimeout(30000);//30秒检测一次
				timeoutManager.start(60000);//延迟60秒启动
			}
		}
	}
	
	private void stopTimeoutManager(){
		if(timeoutManager != null){
			timeoutManager.stop();
		}
	}
	
	/**
	 * 增加会话
	 * @param session
	 * @see #removeSession(int)
	 * @return
	 */
	public synchronized VRSSession putSession(VRSSession session){
		int ssid = session.getSsid();
		session =  sessions.put(ssid, session);
		
		this.startTimeoutManager();
		
		return session;
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(VRSSession)
	 * @return
	 */
	public synchronized VRSSession removeSession(int ssid){
		VRSSession session =  sessions.remove(ssid);
		if(sessions.size() <= 0){
			this.stopTimeoutManager();
		}
		return session;
	}

	/**
	 * 清空所有会话
	 */
	public synchronized void clear(){
		this.sessions.clear();
		this.stopTimeoutManager();
	}
	/**
	 * 获取GK连接会话。
	 * @param ip GKIP地址
	 * @return 如果已登录此GK，返回会话，否则返回null
	 */
	public VRSSession getSessionByIP(String ip){
		VRSSession session = null;
		for(VRSSession s : sessions.values()){
			VRS vrs = s.getVrs();
			String vrsip = vrs != null ? vrs.getIp() : null;
			if(vrsip != null && vrsip.equalsIgnoreCase(ip)){
				session = s;
				break;
			}
		}
		return session;
	}

	/**
	 * 获取连接会话。
	 * @param id
	 * @return
	 */
	public VRSSession getSessionByID(String id){
		VRSSession session = null;
		for(VRSSession s : sessions.values()){
			VRS vrs = s.getVrs();
			String vrsId = vrs != null ? vrs.getId() : null;
			if(vrsId != null && vrsId.equals(id)){
				session = s;
				break;
			}
		}
		return session;
	}
	
	
	/**
	 * 获取VRS连接会话。
	 * @param ssid 会话标识
	 * @return 如果已登录此GK，返回会话，否则返回null
	 */
	public VRSSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<VRSSession> getAllSessions(){
		List<VRSSession> list = new ArrayList<VRSSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
}

/**
 * 会话超时管理
 * @author LinChaoYu
 *
 */
class VRSSessionTimeoutManager extends ATaskThread {
	/**
	 * 超时时间，单位毫秒。默认5分钟
	 */
	private long timeout = 5 * 60 * 1000; 
	VRSSessionManager sessionManager;
	public VRSSessionTimeoutManager(VRSSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<VRSSession> sessions = sessionManager.getAllSessions();
		for(VRSSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				String id = session.getVrs().getId();
				sessionManager.getVrsClient().logout(id);
			}
		}
		
	}
}