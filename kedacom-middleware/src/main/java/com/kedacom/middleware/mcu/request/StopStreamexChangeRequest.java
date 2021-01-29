package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopStreamexChangeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止码流交换
 * @see StopStreamexChangeResponse
 * @author LinChaoYu
 *
 */
public class StopStreamexChangeRequest extends McuRequest {
	
	/**
	 * 会议E164号
	 */
	private String confe164;
	
	/**
	 * 目的终端e164号
	 */
	private String mtdstinfo;
	
	/**
	 * 选看模式 1：视频 2：音频 3：音视频
	 */
	private int mode;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("stopstreamexchange");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.putOpt("mtdstinfo", mtdstinfo);
		data.putOpt("mode", mode);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopStreamexChangeResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtdstinfo() {
		return mtdstinfo;
	}

	public void setMtdstinfo(String mtdstinfo) {
		this.mtdstinfo = mtdstinfo;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}
}
