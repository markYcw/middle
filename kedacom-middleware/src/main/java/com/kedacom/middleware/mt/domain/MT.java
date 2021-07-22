package com.kedacom.middleware.mt.domain;


/**
 * 会议终端
 * @author TaoPeng
 *
 */
public class MT {

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
	 * 终端类型：0-4.7、11-5.0
	 */
	private int type;
	
	/**
	 * 录像机/刻录机/混音器：1/2/3
	 */
	private int vrsType;
	
	/**
	 * vrs2kb刻录机舱门
	 */
	private int serverId;
	
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

	public int getVrsType() {
		return vrsType;
	}

	public void setVrsType(int vrsType) {
		this.vrsType = vrsType;
	}

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void update(MT mt){
		this.setIp(mt.getIp());
		this.setPort(mt.getPort());
		this.setName(mt.getName());
		this.setUsername(mt.getUsername());
		this.setPassword(mt.getPassword());
		this.setType(mt.getType());
		this.setVrsType(mt.getVrsType());
		this.setServerId(mt.getServerId());
	}
	
}
