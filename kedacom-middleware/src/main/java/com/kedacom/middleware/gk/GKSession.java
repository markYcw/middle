package com.kedacom.middleware.gk;

import com.kedacom.middleware.gk.domain.GK;

/**
 * “GK”会话。
 * @author LinChaoYu
 *
 */
public class GKSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * GK
	 */
	private GK gk;
	private GKSessionStatus status;//连接状态（登录状态）
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒
	
	public GKSession(){
		this.status = GKSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
	public GK getGk() {
		return gk;
	}
	public void setGk(GK gk) {
		this.gk = gk;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public boolean isLogin() {
		return status == GKSessionStatus.CONNECTED;
	}
	
	public GKSessionStatus getStatus() {
		return status;
	}

	public void setStatus(GKSessionStatus status) {
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