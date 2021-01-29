package com.kedacom.middleware.mt.domain;
/**
 * 回传端口
 * @author LiPengJia
 *
 */
public class Tcp {

	/**
	 * IP地址
	 */
	private String ip;
	/**
	 * 端口号
	 */
	private int port;
	
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
