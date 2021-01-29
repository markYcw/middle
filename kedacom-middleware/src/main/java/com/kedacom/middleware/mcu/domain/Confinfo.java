package com.kedacom.middleware.mcu.domain;

import java.util.List;

public class Confinfo {
	
	/**
	 * 会议名称
	 */
	private String name;
	
	/**
	 * 会议e164号，可以从GK获取，也可以自动生成。
	 */
	private String e164;
	
	/**
	 * 主席的e164号
	 */
	private String chaire164;
	
	/**
	 * 发言人e164号 
	 */
	private String speakere164;
	
	/**
	 * 会议码率(Kbps)
	 */
	private int bitrate;
	
	/**
	 * 会议持续时间（单位分） 0 表示不自动关
	 */
	private int duration;
	
	/**
	 * 双流百分比
	 */
	private int dstreamscale;
	
	/**
	 * 最小发言持续时间（单位秒）
	 */
	private int talkholdtime;
	
	/**
	 * 是否强拆
	 */
	private boolean isforce;
	
	/**
	 * 会议类型：0-传统，1-端口（5.0特有）
	 */
	private int conftype;
	
	/**
	 * 是否开启全场哑音例外：0-不开启（默认）、1-开启
	 */
	private int mutefilter;
	
	/**
	 * 最大与会终端数目（5.0特有）
	 */
	private int maxmtnum;
	
	private CapSupport  capsupport;
	
	private Attrb attrb;
	
	private List<Mts> mts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public String getChaire164() {
		return chaire164;
	}

	public void setChaire164(String chaire164) {
		this.chaire164 = chaire164;
	}

	public String getSpeakere164() {
		return speakere164;
	}

	public void setSpeakere164(String speakere164) {
		this.speakere164 = speakere164;
	}

	public int getBitrate() {
		return bitrate;
	}

	public void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDstreamscale() {
		return dstreamscale;
	}

	public void setDstreamscale(int dstreamscale) {
		this.dstreamscale = dstreamscale;
	}

	public int getTalkholdtime() {
		return talkholdtime;
	}

	public void setTalkholdtime(int talkholdtime) {
		this.talkholdtime = talkholdtime;
	}

	public boolean isIsforce() {
		return isforce;
	}

	public void setIsforce(boolean isforce) {
		this.isforce = isforce;
	}

	public CapSupport getCapsupport() {
		return capsupport;
	}

	public void setCapsupport(CapSupport capsupport) {
		this.capsupport = capsupport;
	}

	public Attrb getAttrb() {
		return attrb;
	}

	public void setAttrb(Attrb attrb) {
		this.attrb = attrb;
	}

	public List<Mts> getMts() {
		return mts;
	}

	public void setMts(List<Mts> mts) {
		this.mts = mts;
	}

	public int getConftype() {
		return conftype;
	}

	public void setConftype(int conftype) {
		this.conftype = conftype;
	}

	public int getMutefilter() {
		return mutefilter;
	}

	public void setMutefilter(int mutefilter) {
		this.mutefilter = mutefilter;
	}

	public int getMaxmtnum() {
		return maxmtnum;
	}

	public void setMaxmtnum(int maxmtnum) {
		this.maxmtnum = maxmtnum;
	} 
}
