package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 9.1 开始浏览视频
 * 
 * @author dengjie
 * 
 */
public class StartMediaPlayResponse extends CuResponse {

	/**
	 * 监控通道号
	 */
	private int index;
	/**
	 * 视频丢包重传地址ip
	 */
	private String videortcpIp;
	/**
	 * 视频丢包重传地址prot
	 */
	private int videortcpProt;
	/**
	 * 音频丢包重传地址ip
	 */
	private String audiortcpIp;
	/**
	 * 音频丢包重传地址prot
	 */
	private int audiortcpProt;
	/**
	 * 参数是否有效 0无 1视频2音频3音视频
	 */
	private int smbStreamtype;
	/**
	 * 视频类型
	 */
	private int smbVmediatype;
	/**
	 * 时间戳采样率
	 */
	private int smbVclockrate;
	/**
	 * 1定码率2变
	 */
	private int smbVbittype;
	/**
	 * 视频码率
	 */
	private int smbVbitrate;
	/**
	 * 视频宽
	 */
	private int smbVwidth;
	/**
	 * 视频高
	 */
	private int smbVheight;
	/**
	 * 视频帧率
	 */
	private int smbVframerate;
	/**
	 * 音频类型
	 */
	private int smbAmediatype;
	/**
	 * 时间戳采样率
	 */
	private int smbAclockrate;
	/**
	 * 音频码率
	 */
	private int smbAbitrate;
	/**
	 * 音频采样率
	 */
	private int smbAsample;
	/**
	 * 声道数目
	 */
	private int smbAchannel;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.index = jsonData.optInt("index");
		JSONObject videortcp = jsonData.optJSONObject("videortcp");
		this.videortcpIp = videortcp.optString("ip");
		this.videortcpProt = videortcp.optInt("prot");
		JSONObject audiortcp = jsonData.optJSONObject("audiortcp");
		this.audiortcpIp = audiortcp.optString("ip");
		this.audiortcpProt = audiortcp.optInt("prot");
		JSONObject streamattribute = jsonData.optJSONObject("streamattribute");
		this.smbAbitrate = streamattribute.optInt("abitrate");
		this.smbAchannel = streamattribute.optInt("achannel");
		this.smbAclockrate = streamattribute.optInt("aclockrate");
		this.smbAmediatype = streamattribute.optInt("amediatype");
		this.smbAsample = streamattribute.optInt("asample");
		this.smbStreamtype = streamattribute.optInt("streamtype");
		this.smbVbitrate = streamattribute.optInt("vbitrate");
		this.smbVbittype = streamattribute.optInt("vbittype");
		this.smbVclockrate = streamattribute.optInt("vclockrate");
		this.smbVframerate = streamattribute.optInt("vframerate");
		this.smbVheight = streamattribute.optInt("vheight");
		this.smbVmediatype = streamattribute.optInt("vmediatype");
		this.smbVwidth = streamattribute.optInt("vwidth");
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getVideortcpIp() {
		return videortcpIp;
	}

	public void setVideortcpIp(String videortcpIp) {
		this.videortcpIp = videortcpIp;
	}

	public int getVideortcpProt() {
		return videortcpProt;
	}

	public void setVideortcpProt(int videortcpProt) {
		this.videortcpProt = videortcpProt;
	}

	public String getAudiortcpIp() {
		return audiortcpIp;
	}

	public void setAudiortcpIp(String audiortcpIp) {
		this.audiortcpIp = audiortcpIp;
	}

	public int getAudiortcpProt() {
		return audiortcpProt;
	}

	public void setAudiortcpProt(int audiortcpProt) {
		this.audiortcpProt = audiortcpProt;
	}

	public int getSmbStreamtype() {
		return smbStreamtype;
	}

	public void setSmbStreamtype(int smbStreamtype) {
		this.smbStreamtype = smbStreamtype;
	}

	public int getSmbVmediatype() {
		return smbVmediatype;
	}

	public void setSmbVmediatype(int smbVmediatype) {
		this.smbVmediatype = smbVmediatype;
	}

	public int getSmbVclockrate() {
		return smbVclockrate;
	}

	public void setSmbVclockrate(int smbVclockrate) {
		this.smbVclockrate = smbVclockrate;
	}

	public int getSmbVbittype() {
		return smbVbittype;
	}

	public void setSmbVbittype(int smbVbittype) {
		this.smbVbittype = smbVbittype;
	}

	public int getSmbVbitrate() {
		return smbVbitrate;
	}

	public void setSmbVbitrate(int smbVbitrate) {
		this.smbVbitrate = smbVbitrate;
	}

	public int getSmbVwidth() {
		return smbVwidth;
	}

	public void setSmbVwidth(int smbVwidth) {
		this.smbVwidth = smbVwidth;
	}

	public int getSmbVheight() {
		return smbVheight;
	}

	public void setSmbVheight(int smbVheight) {
		this.smbVheight = smbVheight;
	}

	public int getSmbVframerate() {
		return smbVframerate;
	}

	public void setSmbVframerate(int smbVframerate) {
		this.smbVframerate = smbVframerate;
	}

	public int getSmbAmediatype() {
		return smbAmediatype;
	}

	public void setSmbAmediatype(int smbAmediatype) {
		this.smbAmediatype = smbAmediatype;
	}

	public int getSmbAclockrate() {
		return smbAclockrate;
	}

	public void setSmbAclockrate(int smbAclockrate) {
		this.smbAclockrate = smbAclockrate;
	}

	public int getSmbAbitrate() {
		return smbAbitrate;
	}

	public void setSmbAbitrate(int smbAbitrate) {
		this.smbAbitrate = smbAbitrate;
	}

	public int getSmbAsample() {
		return smbAsample;
	}

	public void setSmbAsample(int smbAsample) {
		this.smbAsample = smbAsample;
	}

	public int getSmbAchannel() {
		return smbAchannel;
	}

	public void setSmbAchannel(int smbAchannel) {
		this.smbAchannel = smbAchannel;
	}

}
