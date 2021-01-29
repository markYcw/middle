package com.kedacom.middleware.mcu.domain;

public class Attrb {
	
	/**
	 * 0:不加密，1:des加密，
     8 2:aes加密
	 */
	private int encryptmode;
	
	/**
	 * 0:不开放，1:根据密码加入，2:完全开放
	 */
	private int openmode;
	
	/**
	 * 丢包重传，0:否1:是
	 */
	private int prsmode;
	
	/**
	 * 是否讨论会议，0:不是讨论会议1：是
	 */
	private int discussconf;
	
	/**
	 * 终端入会是否初始哑音
	 */
	private int allinitdumb;
	
	/**
	 * 双流发起方式：0:发言人，1:任意终端
	 */
	private int dualmode;
	
	/**
	 * 会议模式 0:速度优先，1:画质优先
	 */
	private int videomode;
	
	/**
	 * 开会是否自动录像 0-不支持，1支持
	 */
	private int autorec;
	
	/**
	 * 发布模式
	 */
	private int publishmode;
	
	/**
	 * 是否录双流
	 */
	private int isrecdstream;
	
	/**
	 * 是否自动语音激励
	 */
	private int isautovac;
	
	/**
	 * 是否启用适配会议
	 */
	private int ifuseadp;

	public int getEncryptmode() {
		return encryptmode;
	}

	public void setEncryptmode(int encryptmode) {
		this.encryptmode = encryptmode;
	}

	public int getOpenmode() {
		return openmode;
	}

	public void setOpenmode(int openmode) {
		this.openmode = openmode;
	}

	public int getPrsmode() {
		return prsmode;
	}

	public void setPrsmode(int prsmode) {
		this.prsmode = prsmode;
	}

	public int getDiscussconf() {
		return discussconf;
	}

	public void setDiscussconf(int discussconf) {
		this.discussconf = discussconf;
	}

	public int getAllinitdumb() {
		return allinitdumb;
	}

	public void setAllinitdumb(int allinitdumb) {
		this.allinitdumb = allinitdumb;
	}

	public int getDualmode() {
		return dualmode;
	}

	public void setDualmode(int dualmode) {
		this.dualmode = dualmode;
	}

	public int getVideomode() {
		return videomode;
	}

	public void setVideomode(int videomode) {
		this.videomode = videomode;
	}

	public int getAutorec() {
		return autorec;
	}

	public void setAutorec(int autorec) {
		this.autorec = autorec;
	}

	public int getPublishmode() {
		return publishmode;
	}

	public void setPublishmode(int publishmode) {
		this.publishmode = publishmode;
	}

	public int getIsrecdstream() {
		return isrecdstream;
	}

	public void setIsrecdstream(int isrecdstream) {
		this.isrecdstream = isrecdstream;
	}

	public int getIsautovac() {
		return isautovac;
	}

	public void setIsautovac(int isautovac) {
		this.isautovac = isautovac;
	}

	public int getIfuseadp() {
		return ifuseadp;
	}

	public void setIfuseadp(int ifuseadp) {
		this.ifuseadp = ifuseadp;
	}
	
	
}
