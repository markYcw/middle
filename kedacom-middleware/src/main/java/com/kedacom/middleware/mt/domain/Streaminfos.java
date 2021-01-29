package com.kedacom.middleware.mt.domain;


public class Streaminfos {

	private int streamtype;

	private int mediatype;

	private int bitrate;

	private int playload;

	private Netaddr netaddr;

	private Mediacrypt mediacrypt;

	private Videoattr videoattr;

	private Audioattr audioattr;

	public Streaminfos() {
	}

	public Streaminfos(int streamtype, int mediatype, int bitrate,
                       int playload, Netaddr netaddr, Mediacrypt mediacrypt,
                       Videoattr videoattr, Audioattr audioattr) {
		this.streamtype = streamtype;
		this.mediatype = mediatype;
		this.bitrate = bitrate;
		this.playload = playload;
		this.netaddr = netaddr;
		this.mediacrypt = mediacrypt;
		this.videoattr = videoattr;
		this.audioattr = audioattr;
	}

	public int getStreamtype() {
		return streamtype;
	}

	public void setStreamtype(int streamtype) {
		this.streamtype = streamtype;
	}

	public int getMediatype() {
		return mediatype;
	}

	public void setMediatype(int mediatype) {
		this.mediatype = mediatype;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getPlayload() {
		return playload;
	}

	public void setPlayload(int playload) {
		this.playload = playload;
	}

	public Netaddr getNetaddr() {
		return netaddr;
	}

	public void setNetaddr(Netaddr netaddr) {
		this.netaddr = netaddr;
	}

	public Mediacrypt getMediacrypt() {
		return mediacrypt;
	}

	public void setMediacrypt(Mediacrypt mediacrypt) {
		this.mediacrypt = mediacrypt;
	}

	public Videoattr getVideoattr() {
		return videoattr;
	}

	public void setVideoattr(Videoattr videoattr) {
		this.videoattr = videoattr;
	}

	public Audioattr getAudioattr() {
		return audioattr;
	}

	public void setAudioattr(Audioattr audioattr) {
		this.audioattr = audioattr;
	}

}
