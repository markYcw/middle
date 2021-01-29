package com.kedacom.middleware.mcu.domain;

import java.util.List;

/**
 * 录播机-录制任务状态（会议平台5.0及以上版本特有）
 * @author LinChaoYu
 *
 */
public class VcrRecorderStatus {
	
	/**
	 * 录像ID
	 */
	private String recId;

	/**
	 * 录像文件名 最大字符长度：128个字节
	 */
	private String videoName;
	
	/**
	 * 录像类型: 
	   1-会议录像；
       2-终端录像；
	 */
	private int recorderType;
	
	/**
	 * 录像状态 
       0-未录像；
       1-正在录像；
       2-暂停；
       3-正在呼叫实体；
       4-准备录像；
	 */
	private int state;
	
	/**
	 * 是否支持免登陆观看直播 
       0-不支持；
       1-支持；
	 */
	private int anonymous;
	
	/**
	 * 录像模式 
       1-录像；
       2-直播；
       3-录像+直播；
	 */
	private int recorderMode;
	
	/**
	 * 是否录主格式码流（视频+音频） 
       0-否；
       1-是；
	 */
	private int mainStream;
	
	/**
	 * 是否录双流 
       0-否；
       1-是；
	 */
	private int dualStream;
	
	/**
	 * 发布类型 
       0-不发布；
       1-发布
	 */
	private int publishMode;
	
	/**
	 * 当前录像进度（单位为: 秒）
	 */
	private int currentProgress;
	
	/**
	 * 录像终端信息集合
	 */
	private List<String> members;
	

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public int getRecorderType() {
		return recorderType;
	}

	public void setRecorderType(int recorderType) {
		this.recorderType = recorderType;
	}

	public int getMainStream() {
		return mainStream;
	}

	public void setMainStream(int mainStream) {
		this.mainStream = mainStream;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	public int getRecorderMode() {
		return recorderMode;
	}

	public void setRecorderMode(int recorderMode) {
		this.recorderMode = recorderMode;
	}

	public int getDualStream() {
		return dualStream;
	}

	public void setDualStream(int dualStream) {
		this.dualStream = dualStream;
	}

	public int getPublishMode() {
		return publishMode;
	}

	public void setPublishMode(int publishMode) {
		this.publishMode = publishMode;
	}

	public int getCurrentProgress() {
		return currentProgress;
	}

	public void setCurrentProgress(int currentProgress) {
		this.currentProgress = currentProgress;
	}

	public List<String> getMembers() {
		return members;
	}

	public void setMembers(List<String> members) {
		this.members = members;
	}
}
