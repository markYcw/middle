package com.kedacom.middleware.upu.domain;


/**
 * UPU服务器参数信息
 * 
 * @author LinChaoYu
 *
 */
public class UPU {

	/**
	 * UPU本地标识。比如：UPU信息在本地数据库的数据ID
	 */
	private String id;
	
	/**
	 * GK会话标识。登录GK后，中间件返回的服务标识。
	 */
	private int ssid;
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 连接端口
	 */
	private int port;
	
	public void update(UPU gk){
		this.setIp(gk.getIp());
		this.setPort(gk.getPort());
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
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
