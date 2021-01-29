package com.kedacom.middleware.cu;

import com.kedacom.middleware.cu.domain.Cu;
import com.kedacom.middleware.cu.domain.DeviceStatusSubscribe;
import com.kedacom.middleware.cu.domain.PDevice;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * “监控平台”会话管理
 * @author TaoPeng
 *
 */
public class CuSessionManager {
	
	/**
	 * 会话集。
	 * key : 会话标识; value ：会话
	 */
	private Hashtable<Integer, CuSession> sessions = new Hashtable<Integer, CuSession>();
	
	/**设备状态订阅。通过此类设置需要订阅哪些状态*/
	private DeviceStatusSubscribe deviceStatusSubscribe = new DeviceStatusSubscribe();
	
	private CuClient client;
	public CuSessionManager(CuClient client){
		this.client = client;
	}
	
	/**
	 * 增加会话
	 * @param session
	 * @see #removeSession(int)
	 * @return
	 */
	public CuSession putSession(CuSession session){
		int ssid = session.getSsid();
		return sessions.put(ssid, session);
	}
	
	/**
	 * 删除会话
	 * @param ssid 会话标识
	 * @see #putSession(CuSession)
	 * @return
	 */
	public CuSession removeSession(int ssid){
		return sessions.remove(ssid);
	}

	public List<CuSession> getAllSessions(){
		List<CuSession> list = new ArrayList<CuSession>(sessions.size());
		list.addAll(sessions.values());
		return list;
	}
	/**
	 * 获取连接会话。
	 * @param cuIp 监控平台IP地址
	 * @return 如果已登录此监控平台，返回会话，否则返回null
	 */
	public CuSession getSessionByCuIP(String cuIp){
		CuSession session = null;
		for(CuSession s : sessions.values()){
			Cu plt = s.getCu();
			if(plt != null){
				if(cuIp.equalsIgnoreCase(plt.getIp())){
					session = s;
					break;
				}
			}
		}
		return session;
	}

	/**
	 * 获取连接会话。
	 * @param cuId 监控平台ID（一般是本地数据库存储的ID）。id必须大于0，否则返回null
	 * @return 如果已登录此监控平台，返回会话，否则返回null
	 */
	public CuSession getSessionByCuID(int cuId){
		if(cuId <= 0){
			return null;
		}
		CuSession session = null;
		for(CuSession s : sessions.values()){
			Cu plt = s.getCu();
			if(plt != null){
				if(plt.getId() == cuId){
					session = s;
					break;
				}
			}
		}
		return session;
	}
	public int getSSIDByCuID(int cuId){
		CuSession session = this.getSessionByCuID(cuId);
		return session != null ? session.getSsid() : - 1;
	}
	
	/**
	 * 获取会话标识
	 * @param cuIp 监控平台IP地址
	 * @return 如果监控平台已连接，返回ssid，否则返回 {@link CuSession.INVALID_SSID}
	 */
	public int getSSIDByCuIP(String cuIp){
		CuSession session = this.getSessionByCuIP(cuIp);
		return session != null ? session.getSsid() : CuSession.INVALID_SSID;
	}
	/**
	 * 获取监控平台连接会话。
	 * @param ssid 会话标识
	 * @return 如果已登录此终端，返回会话，否则返回null
	 */
	public CuSession getSessionBySSID(int ssid){
		return sessions.get(ssid);
	}
	
	/**
	 * 获取第一个连接的监控平台。适用于整个系统只连接一个监控平台的应用场景。
	 * @return
	 */
	public CuSession getFirstSession(){
		CuSession session = null;
		if(sessions.size() > 0){
			session = sessions.values().iterator().next();
		}
		return session;
	}
	/**
	 * 根据ssid获取监控平台信息
	 * @param ssid
	 * @return
	 */
	public Cu getCuBySSID(int ssid){
		CuSession session = this.getSessionBySSID(ssid);
		if(session != null){
			return session.getCu();
		}else{
			return null;
		}
	}
	
	/**
	 * 根据会话ID（ssid）返回监控平台ID。
	 * @param ssid
	 * @return
	 */
	public int getCuIDBySSID(int ssid){
		Cu cu = this.getCuBySSID(ssid);
		if(cu != null){
			return cu.getId();
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取设备所在会话ID（ssid）
	 * @param puid 设备编号
	 * @return 
	 */
	public int getSSIDByPuid(String puid){
		CuSession session = null;
		for(CuSession s : sessions.values()){
			PDevice device = s.getDeviceCache().getDevice(puid);
			if(device != null){
				session = s;
				break;
			}
		}
		
		if(session != null){
			return session.getSsid();
		}else{
			return CuSession.INVALID_SSID;
		}
	}

	public DeviceStatusSubscribe getDeviceStatusSubscribe() {
		return deviceStatusSubscribe;
	}

	public void setDeviceStatusSubscribe(DeviceStatusSubscribe deviceStatusSubscribe) {
		this.deviceStatusSubscribe = deviceStatusSubscribe;
	}
}
