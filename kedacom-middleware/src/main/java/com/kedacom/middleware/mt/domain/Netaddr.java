package com.kedacom.middleware.mt.domain;


/**
 * 回传地址
 * 
 * @author root
 * 
 */
public class Netaddr {

	/**
	 * 回传地址IP
	 */
	private String ip;

	/**
	 * 回传地址端口
	 */
	private int port;

	public Netaddr() {
	}

	public Netaddr(String ip, int port) {
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
