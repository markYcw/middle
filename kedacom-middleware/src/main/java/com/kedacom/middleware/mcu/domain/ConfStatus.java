package com.kedacom.middleware.mcu.domain;

/**
 * 会议基本状态
 * @author TaoPeng
 *
 */
public class ConfStatus {

	private String confe164;//会议e164
	private String speakermt; //发言人
	private int lockmode;//锁定方式， 0不锁定　1密码锁定 2会控台锁定
	private boolean breggk; //是否注册gk
	private int takemode; //举行方式 0预约 1即时  2会议模板 3虚拟即时会议  4虚拟会议模板
	public String getConfe164() {
		return confe164;
	}
	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}
	public String getSpeakermt() {
		return speakermt;
	}
	public void setSpeakermt(String speakermt) {
		this.speakermt = speakermt;
	}
	public int getLockmode() {
		return lockmode;
	}
	public void setLockmode(int lockmode) {
		this.lockmode = lockmode;
	}
	public boolean isBreggk() {
		return breggk;
	}
	public void setBreggk(boolean breggk) {
		this.breggk = breggk;
	}
	public int getTakemode() {
		return takemode;
	}
	public void setTakemode(int takemode) {
		this.takemode = takemode;
	}
	
	
}
