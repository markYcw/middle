package com.kedacom.middleware.mcu;

import com.kedacom.middleware.mcu.domain.Mcu;

/**
 * “会议平台”会话。
 * @author TaoPeng
 *
 */
public class McuSession {

	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * 会议平台
	 */
	private Mcu mcu;
	private McuSessionStatus status;//连接状态（登录状态）
	
	private long createTime;//会话创建时间，单位：毫秒
	private long lastTime;//最后访问时间，单位：毫秒
	
	public McuSession(){
		this.status = McuSessionStatus.NONE;
		this.createTime = System.currentTimeMillis();
		this.lastTime = createTime;
	}
	public int getSsid() {
		return ssid;
	}
	
	public void setSsid(int ssid) {
		this.ssid = ssid;
	}


	public Mcu getMcu() {
		return mcu;
	}
	public void setMcu(Mcu mcu) {
		this.mcu = mcu;
	}
	public boolean isLogin() {
		return status == McuSessionStatus.CONNECTED;
	}
	public McuSessionStatus getStatus() {
		return status;
	}

	public void setStatus(McuSessionStatus status) {
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

