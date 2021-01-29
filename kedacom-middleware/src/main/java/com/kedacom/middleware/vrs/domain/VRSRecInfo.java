package com.kedacom.middleware.vrs.domain;


/**
 * VRS录像信息
 * @author LinChaoYu
 *
 */
public class VRSRecInfo {
	
	/**
	 * 录像名称
	 */
	private String name;
	
	/**
	 * Rtsp播放链接
	 */
	private String rtspurl;

	/**
	 * 录像创建时间
	 */
	private String createtime;
	
	/**
	 * 录像持续时间(单位:秒)
	 */
	private int duration;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRtspurl() {
		return rtspurl;
	}

	public void setRtspurl(String rtspurl) {
		this.rtspurl = rtspurl;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
