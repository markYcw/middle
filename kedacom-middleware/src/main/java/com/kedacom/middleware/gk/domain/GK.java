package com.kedacom.middleware.gk.domain;


/**
 * GK服务器参数信息
 * @author LinChaoYu
 *
 */
public class GK {

	/**
	 * GK本地标识。比如：GK信息在本地数据库的数据ID
	 */
	private String id;
	
	/**
	 * GK会话标识。登录GK后，中间件返回的服务标识。
	 */
	private int ssid;

	/**
	 * GK名称
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
	
	/**
	 * 是否为管理员
	 */
	private int isadmin;
	
	public void update(GK gk){
		this.setIp(gk.getIp());
		this.setName(gk.getName());
		this.setUsername(gk.getUsername());
		this.setPassword(gk.getPassword());
		this.setPort(gk.getPort());
		this.setIsadmin(gk.getIsadmin());
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

	public int getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
}
