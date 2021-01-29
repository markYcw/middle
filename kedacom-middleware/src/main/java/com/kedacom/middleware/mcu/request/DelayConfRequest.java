package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.DelayConfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 延长会议时长
 * @author LinChaoYu
 *
 */
public class DelayConfRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;

	/**
	 * 延长的时间，单位：分钟
	 */
	private int delaytime;

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}
	
	public int getDelaytime() {
		return delaytime;
	}

	public void setDelaytime(int delaytime) {
		this.delaytime = delaytime;
	}

	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("delayconf");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("delaytime", delaytime);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new DelayConfResponse();
	}

}
