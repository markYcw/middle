package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartTentiremixResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 智能混音
 * @see StartTentiremixResponse
 * @author YueZhipeng
 *
 */
public class StartTentiremixRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startentiremix");
				
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
		return new StartTentiremixResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

}
