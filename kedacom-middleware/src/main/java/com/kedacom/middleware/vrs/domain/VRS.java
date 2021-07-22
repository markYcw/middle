package com.kedacom.middleware.vrs.domain;


/**
 * 录播服务器参数信息
 * @author LinChaoYu
 * @alterBy ycw 2021/5/8 16:11 增加VRS2000B
 */
public class VRS {
	
	//录播服务器软件版本
	public static final String VRS_VERSION_5_0 = "5.0";//5.0版本
	public static final String VRS_VERSION_5_1 = "5.1";//5.1版本
	public static final String VRS_2000B_SV5 = "18";

	/**
	 * 录播服务器本地标识。比如：录播服务器信息在本地数据库的数据ID
	 */
	private String id;
	
	/**
	 * 录播服务器会话标识。登录录播服务器后，中间件返回的服务标识。
	 */
	private int ssid;

	/**
	 * 录播服务器IP地址
	 */
	private String ip;
	
	/**
	 * 软件版本
	 */
	private String version = VRS_VERSION_5_0;
	
	/*
	 * 硬件型号（保留）：VRS2000、VRS2100、VRS4000、VRS2000B等等
	 */
	private String type;
	
	/*
	 * 端口（保留）
	 */
	private Integer port;
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;

	public void update(VRS vrs){
		this.setIp(vrs.getIp());
		this.setVersion(vrs.getVersion());
		this.setPort(vrs.getPort());
		this.setUsername(vrs.getUsername());
		this.setPassword(vrs.getPassword());
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
