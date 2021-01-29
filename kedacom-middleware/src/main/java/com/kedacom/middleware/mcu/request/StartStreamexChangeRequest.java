package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartStreamexChangeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 建立码流交换
 * @see StartStreamexChangeResponse
 * @author LinChaoYu
 *
 */
public class StartStreamexChangeRequest extends McuRequest {
	
	/**
	 * 会议E164号
	 */
	private String confe164;
	
	/**
	 * 源终端e164号
	 */
	private String mtsrcinfo;
	
	/**
	 * 目的终端e164号
	 */
	private String mtdstinfo;
	
	/**
	 * （5.0 接口有效）选看源类型：1-终端， 2-画面合成
	 */
	private int type;
	
	/**
	 * 选看模式  1：视频 
	 *         2：音频 
	 *         3：音视频(5.0 接口无效)
	 */
	private int mode;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startstreamexchange");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", this.confe164);
		data.putOpt("mtsrcinfo", this.mtsrcinfo);
		data.putOpt("mtdstinfo", this.mtdstinfo);
		data.putOpt("mode", this.mode);
		if(this.type != 0)
			data.put("type", this.type);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartStreamexChangeResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtsrcinfo() {
		return mtsrcinfo;
	}

	public void setMtsrcinfo(String mtsrcinfo) {
		this.mtsrcinfo = mtsrcinfo;
	}

	public String getMtdstinfo() {
		return mtdstinfo;
	}

	public void setMtdstinfo(String mtdstinfo) {
		this.mtdstinfo = mtdstinfo;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
