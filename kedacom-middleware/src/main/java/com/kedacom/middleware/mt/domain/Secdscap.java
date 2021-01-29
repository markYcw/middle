package com.kedacom.middleware.mt.domain;

public class Secdscap {

	private int maxbitrate;

	private int mediatype;

	private int resolution;

	private int framerate;

	public Secdscap() {
	}

	public Secdscap(int maxbitrate, int mediatype, int resolution, int framerate) {
		this.maxbitrate = maxbitrate;
		this.mediatype = mediatype;
		this.resolution = resolution;
		this.framerate = framerate;
	}

	public int getMaxbitrate() {
		return maxbitrate;
	}

	public void setMaxbitrate(int maxbitrate) {
		this.maxbitrate = maxbitrate;
	}

	public int getMediatype() {
		return mediatype;
	}

	public void setMediatype(int mediatype) {
		this.mediatype = mediatype;
	}

	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
	}

	public int getFramerate() {
		return framerate;
	}

	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}

}
