package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登出平台
 * @see LogoutResponse
 * @author YueZhipeng
 *
 */
public class LogoutRequest extends McuRequest {
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("logout");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		
		return new LogoutResponse();
	}

}
