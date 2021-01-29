package com.kedacom.middleware.cu;


import com.kedacom.middleware.cu.devicecache.CuDeviceCache;
import com.kedacom.middleware.cu.domain.Cu;

/**
 * “监控平台”会话。一个监控平台一个会话。
 * @see CuSessionManager
 * @author TaoPeng
 *
 */
public class CuSession {

	/**
	 * 无效的SSID
	 */
	public static final int INVALID_SSID = -1;
	
	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * 监控平台信息
	 */
	private Cu cu;

	
	/**
	 * 当前平台域编号（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String cmuno;
	
	/**
	 * 用户编号，客户端在平台的唯一标识，本地实时浏览的时候会用到（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String userno;

	/**
	 * Nat穿越ip（码流接收端和平台不再同一个网段时会用到）（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private String natIp;

	/**
	 * Nat穿越port（仅仅监控平台1.0支持）
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 */
	private int natPort;

	private CuSessionStatus status;//连接状态（登录状态）
	private Exception exception; //连接失败时的错误信息
	
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒
	
	/**
	 * 设备缓存
	 */
	private CuDeviceCache deviceCache;
	
	public CuSession(){
		this.status = CuSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
		this.deviceCache = new CuDeviceCache();
	}
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	public Cu getCu() {
		return cu;
	}
	public void setCu(Cu cu) {
		this.cu = cu;
	}
	public CuSessionStatus getStatus() {
		return status;
	}
	public void setStatus(CuSessionStatus status) {
		this.status = status;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public boolean isLogin() {
		return status == CuSessionStatus.CONNECTED;
	}
	/**
	 * 刷新会话的最后使用时间
	 */
	public void refreshTime(){
		this.lastTime = System.currentTimeMillis();
	}
	public CuDeviceCache getDeviceCache() {
		return deviceCache;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public String getUserno() {
		return userno;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public void setUserno(String userno) {
		this.userno = userno;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public String getCmuno() {
		return cmuno;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public void setCmuno(String cmuno) {
		this.cmuno = cmuno;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public String getNatIp() {
		return natIp;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public void setNatIp(String natIp) {
		this.natIp = natIp;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public int getNatPort() {
		return natPort;
	}
	/**
	 * 
	 * @deprecated 仅仅监控平台1.0支持此属性，而目前为止中间件暂时不支持监控平台1.0
	 * @param cmuno
	 */
	public void setNatPort(int natPort) {
		this.natPort = natPort;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}

	
}
