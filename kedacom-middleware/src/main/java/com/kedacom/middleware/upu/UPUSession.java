package com.kedacom.middleware.upu;

import com.kedacom.middleware.upu.domain.UPU;

/**
 * “UPU”会话。
 * @author LinChaoYu
 *
 */
public class UPUSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * UPU
	 */
	private UPU upu;
	private UPUSessionStatus status;//连接状态（登录状态）
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒
	
	public UPUSession(){
		this.status = UPUSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
	public UPU getUpu() {
		return upu;
	}
	
	public void setUpu(UPU upu) {
		this.upu = upu;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public boolean isLogin() {
		return status == UPUSessionStatus.CONNECTED;
	}
	
	public UPUSessionStatus getStatus() {
		return status;
	}

	public void setStatus(UPUSessionStatus status) {
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