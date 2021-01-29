package com.kedacom.middleware.gk.domain;

import java.util.List;


/**
 * 注册GK服务器终端参数信息
 * @author LinChaoYu
 *
 */
public class Point {

	/**
	 * 0：终端、1：网关、2：MCU、3：GK、4：未定义、5：set
	 */
	private int type;
	
	/**
	 * E164号集合
	 */
	private List<String> e164s;

	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 端口号
	 */
	private int port;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> getE164s() {
		return e164s;
	}

	public void setE164s(List<String> e164s) {
		this.e164s = e164s;
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
