package com.kedacom.middleware.mt.domain;

public class Capsupportex {

	private int videofectype;

	private int audiofectype;

	private int dvideofectype;

	private Secdscap secdscap;

	public Capsupportex() {
	}

	public Capsupportex(int videofectype, int audiofectype, int dvideofectype,
			Secdscap secdscap) {
		this.videofectype = videofectype;
		this.audiofectype = audiofectype;
		this.dvideofectype = dvideofectype;
		this.secdscap = secdscap;
	}

	public int getVideofectype() {
		return videofectype;
	}

	public void setVideofectype(int videofectype) {
		this.videofectype = videofectype;
	}

	public int getAudiofectype() {
		return audiofectype;
	}

	public void setAudiofectype(int audiofectype) {
		this.audiofectype = audiofectype;
	}

	public Secdscap getSecdscap() {
		return secdscap;
	}

	public void setSecdscap(Secdscap secdscap) {
		this.secdscap = secdscap;
	}

	public int getDvideofectype() {
		return dvideofectype;
	}

	public void setDvideofectype(int dvideofectype) {
		this.dvideofectype = dvideofectype;
	}

}
