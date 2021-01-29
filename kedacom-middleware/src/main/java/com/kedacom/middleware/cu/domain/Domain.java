package com.kedacom.middleware.cu.domain;

/**
 * 域。监控平台的域。
 * 
 * @author dengjie
 * 
 */
public class Domain {

	/**
	 * 域ID
	 */
	private String domainid;
	/**
	 * 域名称
	 */
	private String name;
	/**
	 * 父类域ID
	 */
	private String parent;

	public String getDomainid() {
		return domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}
