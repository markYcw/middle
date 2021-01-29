package com.kedacom.middleware.cu.domain;

/**
 * 视频源通道状态
 * 
 * @author dengjie
 * 
 */
public class PChannelStatus {
	/**
	 * 视频源通道号
	 */
	private int sn;
	
	/**
	 * 通道名称
	 */
	private String name;
	
	/**
	 * 是否启用。true启用，false信用，null状态未变化
	 */
	private Boolean enable;
	/**
	 * 是否在线。true上线,false下线，null状态未变化
	 */
	private Boolean online;

	/**
	 * 是否平台录像。true正在平台录像,false不在平台录像，null状态未变化
	 */
	private Boolean platRecord;
	
	/**
	 * 是否前端录像。true正在前端录像,false不在前端录像，null状态未变化
	 */
	private Boolean puRecord;
	
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Boolean getOnline() {
		return online;
	}

	public void setOnline(Boolean online) {
		this.online = online;
	}

	public Boolean getPlatRecord() {
		return platRecord;
	}

	public void setPlatRecord(Boolean platRecord) {
		this.platRecord = platRecord;
	}

	public Boolean getPuRecord() {
		return puRecord;
	}

	public void setPuRecord(Boolean puRecord) {
		this.puRecord = puRecord;
	}

}
