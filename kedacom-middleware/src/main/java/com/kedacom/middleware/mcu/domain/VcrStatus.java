package com.kedacom.middleware.mcu.domain;

import java.util.List;

/**
 * 录像机状态（会议平台4.7版本特有）
 * @author root
 *
 */
public class VcrStatus {

	/**
	 * 是否在线
	 */
	private boolean isonline;
	
	/**
	 * 现在存的是EqpId
	 */
	private int recabsid;
	
	/**
	 * 绑定的apsid
	 */
	private int apsid;
	
	/**
	 * Ip地址
	 */
	private String ip;
	
	/**
	 * 别名
	 */
	private String alias;
	
	/**
	 * 录像信道配置数
	 */
	private int recchnnum;
	
	/**
	 * 放像信道配置数
	 */
	private int playchnnum;
	
	/**
	 * 是否支持发布
	 */
	private boolean isspublic;
	
	/**
	 * 是否是oem录像机
	 */
	private boolean isoem;
	
	/**
	 * 剩余空间(单位M)
	 */
	private int freespace;
	
	/**
	 * 总空间(单位M)
	 */
	private int totalspace;
	
	/**
	 * 通道状态
	 */
	private List<VcrChnStatus> vcrChnStatus;
	
	public boolean isIsonline() {
		return isonline;
	}
	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}
	public int getRecabsid() {
		return recabsid;
	}
	public void setRecabsid(int recabsid) {
		this.recabsid = recabsid;
	}
	public int getApsid() {
		return apsid;
	}
	public void setApsid(int apsid) {
		this.apsid = apsid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public int getRecchnnum() {
		return recchnnum;
	}
	public void setRecchnnum(int recchnnum) {
		this.recchnnum = recchnnum;
	}
	public int getPlaychnnum() {
		return playchnnum;
	}
	public void setPlaychnnum(int playchnnum) {
		this.playchnnum = playchnnum;
	}
	public boolean isIsspublic() {
		return isspublic;
	}
	public void setIsspublic(boolean isspublic) {
		this.isspublic = isspublic;
	}
	public boolean isIsoem() {
		return isoem;
	}
	public void setIsoem(boolean isoem) {
		this.isoem = isoem;
	}
	public int getFreespace() {
		return freespace;
	}
	public void setFreespace(int freespace) {
		this.freespace = freespace;
	}
	public int getTotalspace() {
		return totalspace;
	}
	public void setTotalspace(int totalspace) {
		this.totalspace = totalspace;
	}
	public List<VcrChnStatus> getVcrChnStatus() {
		return vcrChnStatus;
	}
	public void setVcrChnStatus(List<VcrChnStatus> vcrChnStatus) {
		this.vcrChnStatus = vcrChnStatus;
	}

}
