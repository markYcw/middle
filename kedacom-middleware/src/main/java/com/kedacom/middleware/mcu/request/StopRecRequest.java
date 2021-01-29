package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止录像
 * @see StopRecResponse
 * @author YueZhipeng
 * 
 * 2018-02-02 LinChaoYu 适配会议平台5.0及以上版本
 * 
 */
public class StopRecRequest extends McuRequest {

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
	 * 录像ID（5.0）
	 */
	private String recid;
	
	/**
	 * 录像模式 （5.0）
	 * 1-录像；2-直播；3-录像+直播
	 */
	private int recordermode;

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("stoprec");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("vcrname", vcrname);
		data.put("filename", filename);
		data.put("isconf", isconf);
		
		data.put("recid", recid);
		data.put("recordermode", recordermode);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopRecResponse();
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

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}

	public int getRecordermode() {
		return recordermode;
	}

	public void setRecordermode(int recordermode) {
		this.recordermode = recordermode;
	}
}
