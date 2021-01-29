package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopVmpResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止画面合成
 * 
 * @see StopVmpResponse
 * @author YueZhipeng
 * 
 */
public class StopVmpRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;

	@Override
	public String toJson() throws JSONException {

		// Req部分
		JSONObject req = super.buildReq("stopvmp");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);

		// 返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new StopVmpResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

}
