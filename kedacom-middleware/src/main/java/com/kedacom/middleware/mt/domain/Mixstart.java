package com.kedacom.middleware.mt.domain;

public class Mixstart {

	private int groupid;

	private int depth;

	private int audiomode;

	private Mediaencryp mediaencryp;

	private int needprs;

	public Mixstart() {
	}

	public Mixstart(int groupid, int depth, int audiomode,
                    Mediaencryp mediaencryp, int needprs) {
		this.groupid = groupid;
		this.depth = depth;
		this.audiomode = audiomode;
		this.mediaencryp = mediaencryp;
		this.needprs = needprs;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getAudiomode() {
		return audiomode;
	}

	public void setAudiomode(int audiomode) {
		this.audiomode = audiomode;
	}

	public Mediaencryp getMediaencryp() {
		return mediaencryp;
	}

	public void setMediaencryp(Mediaencryp mediaencryp) {
		this.mediaencryp = mediaencryp;
	}

	public int getNeedprs() {
		return needprs;
	}

	public void setNeedprs(int needprs) {
		this.needprs = needprs;
	}

}
