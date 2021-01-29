package com.kedacom.middleware.mt;

import com.kedacom.middleware.mt.domain.MT;

public class MTSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	private MT mt;//终端信息
	
	private MTSessionStatus status;//连接状态
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒 

	public MTSession(){
		this.status = MTSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	
	public boolean isLogin() {
		return status == MTSessionStatus.CONNECTED;
	}
	
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	public MT getMt() {
		return mt;
	}
	public void setMt(MT mt) {
		this.mt = mt;
	}

	/**
	 * @deprecated replace by {@link #setStatus(MTSessionStatus)}}
	 */ 
	public void setStatus(int status) {
		if(status == 0){
			this.status = MTSessionStatus.NONE;
		}else if(status == 1){
			this.status = MTSessionStatus.CONNECTING;
		}else if(status == 2){
			this.status = MTSessionStatus.CONNECTED;
		}else if(status == 3){
			this.status = MTSessionStatus.FAILED;
		}else if(status == 4){
			this.status = MTSessionStatus.disconnect;
		}
	}

	public MTSessionStatus getStatus() {
		return status;
	}

	public void setStatus(MTSessionStatus status) {
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
