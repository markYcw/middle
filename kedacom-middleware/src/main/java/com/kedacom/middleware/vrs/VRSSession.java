package com.kedacom.middleware.vrs;

import com.kedacom.middleware.vrs.domain.VRS;

/**
 * “录播服务器”会话。
 * @author LinChaoYu
 *
 */
public class VRSSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * VRS
	 */
	private VRS vrs;
	private VRSSessionStatus status;//连接状态（登录状态）
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒
	
	public VRSSession(){
		this.status = VRSSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
	public VRS getVrs() {
		return vrs;
	}
	public void setVrs(VRS vrs) {
		this.vrs = vrs;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public boolean isLogin() {
		return status == VRSSessionStatus.CONNECTED;
	}
	
	public VRSSessionStatus getStatus() {
		return status;
	}

	public void setStatus(VRSSessionStatus status) {
		this.status = status;
	}

	public long getCreateTime() {
		return createTime;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * 刷新会话的最后使用时间
	 */
	public void refreshTime(){
		this.lastTime = System.currentTimeMillis();
	}

}