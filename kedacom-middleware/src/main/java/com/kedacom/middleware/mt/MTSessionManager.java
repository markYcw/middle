package com.kedacom.middleware.mt;

import com.kedacom.middleware.mt.domain.MT;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * 终端会话管理
 * @author TaoPeng
 *
 */
public class MTSessionManager {

	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, MTSession> sessions = new Hashtable<Integer, MTSession>();

	private MTClient mtClient;
	private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
	private MTSessionTimeoutManager timeoutManager;
	
	public MTSessionManager(MTClient mtClient){
		this.mtClient = mtClient;
	}
	
	protected MTClient getMtClient() {
		return mtClient;
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
				timeoutManager.setName("MT-SessionManager");
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
	public synchronized MTSession putSession(MTSession session){
		int ssid = session.getSsid();
		session =  sessions.put(ssid, session);
		
		this.startTimeoutManager();
		
		return session;
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(MTSession)
	 * @return
	 */
	public synchronized MTSession removeSession(int ssid){
		MTSession session =  sessions.remove(ssid);
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
	 * @param ip 终端IP地址
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public MTSession getSessionByIP(String ip){
		MTSession session = null;
		for(MTSession s : sessions.values()){
			MT mt = s.getMt();
			String mtip = mt != null ? mt.getIp() : null;
			if(mtip != null && mtip.equalsIgnoreCase(ip)){
				session = s;
				break;
			}
		}
		return session;
	}
	/**
	 * 获取终端连接会话,VRS2KB。
	 * @param ip 终端IP地址
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public MTSession getSessionByIP(String ip,int vrsType){
		MTSession session = null;
		for(MTSession s : sessions.values()){
			MT mt = s.getMt();
			String mtip = mt != null ? mt.getIp() : null;
			if(mtip != null && mtip.equalsIgnoreCase(ip) && vrsType == mt.getVrsType()){
				session = s;
				break;
			}
		}
		return session;
	}
	
	/**
	 * 获取终端连接会话,VRS2KB,带舱门参数。
	 * @param ip 终端IP地址
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public MTSession getSessionByIP(String ip,int vrsType,int serverId){
		MTSession session = null;
		for(MTSession s : sessions.values()){
			MT mt = s.getMt();
			String mtip = mt != null ? mt.getIp() : null;
			if(mtip != null && mtip.equalsIgnoreCase(ip) && vrsType == mt.getVrsType() && serverId == mt.getServerId()){
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
	public MTSession getSessionByID(String id){
		MTSession session = null;
		for(MTSession s : sessions.values()){
			MT mt = s.getMt();
			String mtId = mt != null ? mt.getId() : null;
			if(mtId != null && mtId.equals(id)){
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
	public MTSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<MTSession> getAllSessions(){
		List<MTSession> list = new ArrayList<MTSession>(sessions.size());
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
	MTSessionManager sessionManager;
	public MTSessionTimeoutManager(MTSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<MTSession> sessions = sessionManager.getAllSessions();
		for(MTSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				if(session.getMt() != null){
					String id = session.getMt().getId();
					sessionManager.getMtClient().logout(id);
				}
			}
		}
		
	}
}