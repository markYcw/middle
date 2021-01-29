package com.kedacom.middleware.upu;

import com.kedacom.middleware.upu.domain.UPU;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * UPU 会话管理
 * 
 * @author LinChaoYu
 *
 */
public class UPUSessionManager {

	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, UPUSession> sessions = new Hashtable<Integer, UPUSession>();

	private UPUClient upuClient;
	private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
	private UPUSessionTimeoutManager timeoutManager;
	
	public UPUSessionManager(UPUClient upuClient){
		this.upuClient = upuClient;
	}
	
	protected UPUClient getUpuClient() {
		return upuClient;
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
				timeoutManager = new UPUSessionTimeoutManager(this);
				timeoutManager.setDaemon(true);
				timeoutManager.setName("UPU-SessionManager");
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
	public synchronized UPUSession putSession(UPUSession session){
		int ssid = session.getSsid();
		session =  sessions.put(ssid, session);
		
		this.startTimeoutManager();
		
		return session;
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(UPUSession)
	 * @return
	 */
	public synchronized UPUSession removeSession(int ssid){
		UPUSession session =  sessions.remove(ssid);
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
	 * 获取UPU连接会话。
	 * @param ip UPU的IP地址
	 * @return 如果已登录此UPU，返回会话，否则返回null
	 */
	public UPUSession getSessionByIP(String ip){
		UPUSession session = null;
		for(UPUSession s : sessions.values()){
			UPU upu = s.getUpu();
			String upuIp = (upu != null ? upu.getIp() : null);
			if(upuIp != null && upuIp.equalsIgnoreCase(ip)){
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
	public UPUSession getSessionByID(String id){
		UPUSession session = null;
		for(UPUSession s : sessions.values()){
			UPU upu = s.getUpu();
			String upuId = (upu != null ? upu.getId() : null);
			if(upuId != null && upuId.equals(id)){
				session = s;
				break;
			}
		}
		return session;
	}
	
	
	/**
	 * 获取UPU连接会话。
	 * @param ssid 会话标识
	 * @return 如果已登录此UPU，返回会话，否则返回null
	 */
	public UPUSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<UPUSession> getAllSessions(){
		List<UPUSession> list = new ArrayList<UPUSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
}

/**
 * 会话超时管理
 * @author LinChaoYu
 *
 */
class UPUSessionTimeoutManager extends ATaskThread {
	/**
	 * 超时时间，单位毫秒。默认5分钟
	 */
	private long timeout = 5 * 60 * 1000; 
	UPUSessionManager sessionManager;
	public UPUSessionTimeoutManager(UPUSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<UPUSession> sessions = sessionManager.getAllSessions();
		for(UPUSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				String id = session.getUpu().getId();
				sessionManager.getUpuClient().logout(id);
			}
		}
		
	}
}