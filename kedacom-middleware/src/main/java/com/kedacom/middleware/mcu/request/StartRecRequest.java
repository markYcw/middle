package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开始录像
 * 
 * @see StartRecResponse
 * @author YueZhipeng
 * 
 * 2018-02-02 LinChaoYu 适配会议平台5.0及以上版本
 */
public class StartRecRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 终端e164或 IP
	 */
	private String mtinfo;
	
	/**
	 * 录像机名称
	 */
	private String vcrname;
	
	/**
	 * 录像文件名称
	 */
	private String filename;
	
	/**
	 * 是否是会议录像 False: 终端录像
	 */

	private boolean isconf;
	
	/**
	 * 发布模式（5.0）
	 * 0-不发布；1-发布
	 */
	private int publishmode;
	
	/**
	 * 是否支持免登陆观看直播（5.0）
	 * 0-不支持；1-支持；
	 */
	private int anonymous;
	
	/**
	 * 录像模式 （5.0）
	 * 1-录像；2-直播；3-录像+直播；
	 */
	private int recordermode;
	
	/**
	 * 是否录主格式码流（视频+音频） （5.0）
	 * 0-否；1-是；
	 */
	private int mainstream;
	
	/**
	 * 是否录双流（仅双流） （5.0）
	 * 0-否；1-是；
	 */
	private int dualstream;

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startrec");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("vcrname", vcrname);
		data.put("filename", filename);
		data.put("isconf", isconf);
		
		data.put("publishmode", publishmode);
		data.put("anonymous", anonymous);
		data.put("recordermode", recordermode);
		data.put("mainstream", mainstream);
		data.put("dualstream", dualstream);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartRecResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public String getVcrname() {
		return vcrname;
	}

	public void setVcrname(String vcrname) {
		this.vcrname = vcrname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public boolean isIsconf() {
		return isconf;
	}

	public void setIsconf(boolean isconf) {
		this.isconf = isconf;
	}

	public int getPublishmode() {
		return publishmode;
	}

	public void setPublishmode(int publishmode) {
		this.publishmode = publishmode;
	}

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	public int getRecordermode() {
		return recordermode;
	}

	public void setRecordermode(int recordermode) {
		this.recordermode = recordermode;
	}

	public int getMainstream() {
		return mainstream;
	}

	public void setMainstream(int mainstream) {
		this.mainstream = mainstream;
	}

	public int getDualstream() {
		return dualstream;
	}

	public void setDualstream(int dualstream) {
		this.dualstream = dualstream;
	}

}
