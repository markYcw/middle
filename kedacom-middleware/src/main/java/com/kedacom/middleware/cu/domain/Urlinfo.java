package com.kedacom.middleware.cu.domain;

public class Urlinfo {
	//播放通道
	private String url;
	//厂商
	private String factory;
	//类型
	private int streamtype;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	public int getStreamtype() {
		return streamtype;
	}
	public void setStreamtype(int streamtype) {
		this.streamtype = streamtype;
	}
}
