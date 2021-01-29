package com.kedacom.middleware.gk;

import com.kedacom.middleware.gk.domain.GK;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * GK会话管理
 * @author LinChaoYu
 *
 */
public class GKSessionManager {

	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, GKSession> sessions = new Hashtable<Integer, GKSession>();

	private GKClient gkClient;
	private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
	private GKSessionTimeoutManager timeoutManager;
	
	public GKSessionManager(GKClient gkClient){
		this.gkClient = gkClient;
	}
	
	protected GKClient getGkClient() {
		return gkClient;
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
				timeoutManager = new GKSessionTimeoutManager(this);
				timeoutManager.setDaemon(true);
				timeoutManager.setName("GK-SessionManager");
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
	public synchronized GKSession putSession(GKSession session){
		int ssid = session.getSsid();
		session =  sessions.put(ssid, session);
		
		this.startTimeoutManager();
		
		return session;
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(GKSession)
	 * @return
	 */
	public synchronized GKSession removeSession(int ssid){
		GKSession session =  sessions.remove(ssid);
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
	public GKSession getSessionByIP(String ip){
		GKSession session = null;
		for(GKSession s : sessions.values()){
			GK gk = s.getGk();
			String gkip = gk != null ? gk.getIp() : null;
			if(gkip != null && gkip.equalsIgnoreCase(ip)){
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
	public GKSession getSessionByID(String id){
		GKSession session = null;
		for(GKSession s : sessions.values()){
			GK gk = s.getGk();
			String gkId = gk != null ? gk.getId() : null;
			if(gkId != null && gkId.equals(id)){
				session = s;
				break;
			}
		}
		return session;
	}
	
	
	/**
	 * 获取GK连接会话。
	 * @param ssid 会话标识
	 * @return 如果已登录此GK，返回会话，否则返回null
	 */
	public GKSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<GKSession> getAllSessions(){
		List<GKSession> list = new ArrayList<GKSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
}

/**
 * 会话超时管理
 * @author LinChaoYu
 *
 */
class GKSessionTimeoutManager extends ATaskThread {
	/**
	 * 超时时间，单位毫秒。默认5分钟
	 */
	private long timeout = 5 * 60 * 1000; 
	GKSessionManager sessionManager;
	public GKSessionTimeoutManager(GKSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<GKSession> sessions = sessionManager.getAllSessions();
		for(GKSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				String id = session.getGk().getId();
				sessionManager.getGkClient().logout(id);
			}
		}
		
	}
}