package com.kedacom.middleware.mcu;

import com.kedacom.middleware.mcu.domain.Mcu;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “会议平台”会话管理
 * @author TaoPeng
 *
 */
public class McuSessionManager {

	/**
	 * 会话集。
	 * key : ssid会话标识; value ：会话
	 */
	private Hashtable<Integer, McuSession> sessions = new Hashtable<Integer, McuSession>();
	private McuClient mcuClient;
	private boolean sessionTimeoutEnable = false;//是否支持会话超时，默认false(不支持).
	private McuSessionTimeoutManager timeoutManager;
	public McuSessionManager(McuClient mcuClient){
		this.mcuClient = mcuClient;
		this.start();
	}
	
	public McuClient getMcuClient() {
		return mcuClient;
	}

	public boolean isSessionTimeoutEnable() {
		return sessionTimeoutEnable;
	}

	public void setSessionTimeoutEnable(boolean sessionTimeoutEnable) {
		this.sessionTimeoutEnable = sessionTimeoutEnable;
	}

	public void start(){
		if(sessionTimeoutEnable){
			if(timeoutManager == null){
				timeoutManager = new McuSessionTimeoutManager(this);
				timeoutManager.setDaemon(true);
				timeoutManager.setName("MCU-SessionManager");
				timeoutManager.setTimeout(30000);//30秒检测一次
//				timeoutManager.start(60000);//延迟60秒启动 （会话超时机制，暂时不启用）
			}
		}
	}
	
	public void stop(){
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
	public McuSession putSession(McuSession session){
		int ssid = session.getSsid();
		return sessions.put(ssid, session);
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(McuSession)
	 * @return
	 */
	public McuSession removeSession(int ssid){
		return sessions.remove(ssid);
	}
	
//	/**
//	 * 获取连接会话。
//	 * @param ip 终端IP地址
//	 * @return 如果已登录此终端，返回会话，否则返回null
//	 */
//	public McuSession getSessionByIP(String ip){
//		McuSession session = null;
//		for(McuSession s : sessions.values()){
//			Mcu mcu = s.getMcu();
//			String mcuIp = mcu != null ? mcu.getIp() : null;
//			if(mcuIp != null && mcuIp.equalsIgnoreCase(ip)){
//				session = s;
//				break;
//			}
//		}
//		return session;
//	}
//	
	/**
	 * 获取连接会话。
	 * @param id
	 * @return
	 */
	public McuSession getSessionByID(String id){
		McuSession session = null;
		for(McuSession s : sessions.values()){
			Mcu mcu = s.getMcu();
			String mcuId = mcu != null ? mcu.getId() : null;
			if(mcuId != null && mcuId.equals(id)){
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
	public McuSession getSessionBySsid(int ssid){
		return sessions.get(ssid);
	}
	
	public List<McuSession> getAllSessions(){
		List<McuSession> list = new ArrayList<McuSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
}

/**
 * 会话超时管理
 * @author TaoPeng
 *
 */
class McuSessionTimeoutManager extends ATaskThread {
	/**
	 * 超时时间，单位毫秒。默认5分钟
	 */
	private long timeout = 5 * 60 * 1000; 
	McuSessionManager sessionManager;
	public McuSessionTimeoutManager(McuSessionManager sessionManager){
		this.sessionManager = sessionManager;
	}
	@Override
	public void doWork() throws Exception {
		
		List<McuSession> sessions = sessionManager.getAllSessions();
		for(McuSession session : sessions){
			long lastTime = session.getLastTime();
			long currTime = System.currentTimeMillis();
			if(currTime - lastTime >= timeout){
				//会话超时
				String id = session.getMcu().getId();
				sessionManager.getMcuClient().logout(id);
			}
		}
		
	}
}