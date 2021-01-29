package com.kedacom.middleware.svr;

import com.kedacom.middleware.svr.domain.SVR;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * SVR会话管理
 * @author DengJie
 *
 */
public class SVRSessionManager {

	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, SVRSession> sessions = new Hashtable<Integer, SVRSession>();

	private SVRClient svrClient;
	private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
	private MTSessionTimeoutManager timeoutManager;
	
	public SVRSessionManager(SVRClient svrClient){
		this.svrClient = svrClient;
	}
	
	protected SVRClient getSVRClient() {
		return svrClient;
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
				timeoutManager = new MTSessionTimeoutManager(this);
				timeoutManager.setDaemon(true);
				timeoutManager.setName("SVR-SessionManager");
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
	public synchronized SVRSession putSession(SVRSession session){
		int ssid = session.getSsid();
		session =  sessions.put(ssid, session);
		
		this.startTimeoutManager();
		
		return session;
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(SVRSession)
	 * @return
	 */
	public synchronized SVRSession removeSession(int ssid){
		SVRSession session =  sessions.remove(ssid);
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
	 * 获取终端连接会话。
	 * @param ip SVRIP地址
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public SVRSession getSessionByIP(String ip){
		SVRSession session = null;
		for(SVRSession s : sessions.values()){
			SVR svr = s.getSvr();
			String svrip = svr != null ? svr.getIp() : null;
			if(svrip != null && svrip.equalsIgnoreCase(ip)){
				session = s;
				break;
			}
		}
		return session;
	}
	/**
	 * 获取终端连接会话。
	 * @param ssid 会话标识
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public SVRSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<SVRSession> getAllSessions(){
		List<SVRSession> list = new ArrayList<SVRSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
}

/**
 * 会话超时管理
 * @author TaoPeng
 *
 */
class MTSessionTimeoutManager extends ATaskThread {
	/**
	 * 超时时间，单位毫秒。默认5分钟
	 */
	private long timeout = 5 * 60 * 1000; 
	SVRSessionManager sessionManager;
	public MTSessionTimeoutManager(SVRSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<SVRSession> sessions = sessionManager.getAllSessions();
		for(SVRSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				if(session.getSvr() != null){
					String ip = session.getSvr().getIp();
					sessionManager.getSVRClient().logout(ip);
				}
			}
		}
		
	}
}