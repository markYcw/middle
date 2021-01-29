package com.kedacom.middleware.svr;

import com.kedacom.middleware.svr.domain.SVR;

public class SVRSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	private SVR svr;//终端信息
	
	private SVRSessionStatus status;//连接状态
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒 

	public SVRSession(){
		this.status = SVRSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	
	public boolean isLogin() {
		return status == SVRSessionStatus.CONNECTED;
	}
	
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}


	public SVR getSvr() {
		return svr;
	}

	public void setSvr(SVR svr) {
		this.svr = svr;
	}


	public SVRSessionStatus getStatus() {
		return status;
	}

	public void setStatus(SVRSessionStatus status) {
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
	/**
	 * 刷新会话的最后使用时间
	 */
	public void refreshTime(){
		this.lastTime = System.currentTimeMillis();
	}
	
}
