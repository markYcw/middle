package com.kedacom.middleware.mcu.domain;


/**
 * 平台注册终端信息（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class Terminal {
	
    /**
     * 终端所在域ID
     */
	private String domainMoid;
	
	/**
	 * 域名称
	 */
	private String name;
	
	/**
	 * 终端设备域ID
	 */
	private String moid;
	
	/**
	 * 终端设备E164
	 */
	private String e164;
	
	/**
	 * 终端设备在线状态
	 * online(在线)，offline(离线)
	 */
	private String online;
	
	/**
	 * 终端类型
	 * 三代高清终端列表(非受管)
	 */
	private String type;
	
	/**
	 * 终端的版本
	 * 三代高清终端列表(非受管)
	 */
	private String version;
	
	/**
	 * 终端的IP
	 * 三代高清终端列表(非受管)
	 */
	private String ip;

	public String getDomainMoid() {
		return domainMoid;
	}

	public void setDomainMoid(String domainMoid) {
		this.domainMoid = domainMoid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public String getOnline() {
		return online;
	}

	public void setOnline(String online) {
		this.online = online;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
