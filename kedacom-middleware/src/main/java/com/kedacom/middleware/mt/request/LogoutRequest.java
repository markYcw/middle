package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 注销。
 * @author TaoPeng
 * @see LogoutResponse
 *
 */
public class LogoutRequest extends MTRequest {

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("logout");

		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);

		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new LogoutResponse();
	}
	
}
