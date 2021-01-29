package com.kedacom.middleware.mt.domain;


/**
 * 音频信息
 * 
 * @author root
 * 
 */
public class Audioattr {

	/**
	 * 采样率
	 */
	private int samplerate;

	/**
	 * 音频声道数
	 */
	private int tracknum;

	public Audioattr() {
	}

	public Audioattr(int samplerate, int tracknum) {
		this.samplerate = samplerate;
		this.tracknum = tracknum;
	}

	public int getSamplerate() {
		return samplerate;
	}

	public void setSamplerate(int samplerate) {
		this.samplerate = samplerate;
	}

	public int getTracknum() {
		return tracknum;
	}

	public void setTracknum(int tracknum) {
		this.tracknum = tracknum;
	}

}
