package com.kedacom.middleware.mt.domain;


/**
 * 录像属性
 * 
 * @author root
 * 
 */
public class Videoattr {

	/**
	 * 分辨率
	 */
	private int res;

	/**
	 * profile类型
	 */
	private int profiletype;

	/**
	 * 帧率
	 */
	private int framerate;

	public Videoattr() {
	}

	public Videoattr(int res, int profiletype, int framerate) {
		this.res = res;
		this.profiletype = profiletype;
		this.framerate = framerate;
	}

	public int getRes() {
		return res;
	}

	public void setRes(int res) {
		this.res = res;
	}

	public int getProfiletype() {
		return profiletype;
	}

	public void setProfiletype(int profiletype) {
		this.profiletype = profiletype;
	}

	public int getFramerate() {
		return framerate;
	}

	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}

}
