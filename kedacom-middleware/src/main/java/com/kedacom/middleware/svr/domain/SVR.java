package com.kedacom.middleware.svr.domain;


/**
 * SVR设备参数
 * @author DengJie
 *
 */
public class SVR {

	/**
	 * SVR会话标识。登录SVR后，中间件返回的服务标识。
	 */
	private int ssid;

	/**
	 * SVR名称
	 */
	private String name;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 连接端口
	 */
	private int port;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	/*
	类型
	 */
	private int devType;
	
	
	public void update(SVR svr){
		this.setIp(svr.getIp());
		this.setName(svr.getName());
		this.setUsername(svr.getUsername());
		this.setPassword(svr.getPassword());
		this.setPort(svr.getPort());
		this.setDevType(svr.getDevType());
	}
	
	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDevType() {
		return devType;
	}

	public void setDevType(int devType) {
		this.devType = devType;
	}
}
