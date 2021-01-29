package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 2. 登出平台
 * 
 * @author dengjie
 * @see LogoutResponse
 */
public class LogoutRequest extends CuRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("logout");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new LogoutResponse();
	}

}
