package com.kedacom.middleware.mt.domain;

public class Transaddress {

	private String ip;

	private int port;

	public Transaddress() {
	}

	public Transaddress(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
