package com.kedacom.middleware.cu.domain;

/**
 * 监控平台
 * @author TaoPeng
 *
 */
public class Platform {

	/**
	 * 监控平台本地标识。比如：MT信息在本地数据库的数据ID
	 */
	private int id;

	/**
	 * 监控平台名称
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
