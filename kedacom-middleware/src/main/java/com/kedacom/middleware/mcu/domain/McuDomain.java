package com.kedacom.middleware.mcu.domain;


/**
 * 平台域（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class McuDomain {
	
    /**
     * 域ID
     */
	private String moid;
	
	/**
	 * 上级域Id
	 */
	private String parentMoid;
	
	/**
	 * 域名称
	 */
	private String name;
	
	/**
	 * 域的类型
	 * kernel(核心域), service(服务域), platform(平台域), user(用户域)
	 */
	private String type;

	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}

	public String getParentMoid() {
		return parentMoid;
	}

	public void setParentMoid(String parentMoid) {
		this.parentMoid = parentMoid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
