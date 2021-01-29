package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetconfvmpstatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取画面合成状态
 * @author root
 *
 */
public class GetconfvmpstatusRequest extends McuRequest {

	private String e164;

	@Override
	public String toJson() throws JSONException {
		
		JSONObject req = super.buildReq("getconfvmpstatus");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", e164);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetconfvmpstatusResponse();
	}

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

}
