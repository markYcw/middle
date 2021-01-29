package com.kedacom.middleware.cu.domain;

import com.kedacom.middleware.DeviceType;


/**
 * 监控平台（相当于原来的KPlatform）
 * @author TaoPeng
 *
 */
public class Cu {

	public static final int CU1 = DeviceType.CU.toInt();
	public static final int CU2 = DeviceType.CU2.toInt();
	
	private int version = CU2;
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
	 * 连接端口。平台1.0端口默认1722,平台1.0端口默认80
	 */
	private int port = 80;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	public void update(Cu cu){
		this.setIp(cu.getIp());
		this.setPort(cu.getPort());
		this.setName(cu.getName());
		this.setUsername(cu.getUsername());
		this.setPassword(cu.getPassword());
		this.setVersion(cu.getVersion());
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

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
