package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopMixResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止混音
 * @see StopMixResponse
 * @author YueZhipeng
 *
 */
public class StopMixRequest extends McuRequest {
    
	private String confe164;
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("stopmix");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopMixResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

}
