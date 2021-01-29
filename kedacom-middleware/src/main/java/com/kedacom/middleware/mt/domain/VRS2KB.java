package com.kedacom.middleware.mt.domain;


/**
 * 会议终端
 * @author TaoPeng
 *
 */
public class VRS2KB extends MT{

	/**
	 * 终端本地标识。比如：MT信息在本地数据库的数据ID
	 */
	private String id;
	
	/**
	 * 终端会话标识。登录终端后，中间件返回的服务标识。
	 */
	private int ssid;

	/**
	 * 终端名称
	 */
	private String name;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	private int devType;
	
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

	public void update(VRS2KB mt){
		this.setIp(mt.getIp());
		this.setName(mt.getName());
		this.setUsername(mt.getUsername());
		this.setPassword(mt.getPassword());
		this.setDevType(mt.getDevType());
	}
	
}
