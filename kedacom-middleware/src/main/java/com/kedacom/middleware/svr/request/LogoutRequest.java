package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登出 SVR服务器
 * @see LogoutResponse
 * @author DengJie
 *
 */
public class LogoutRequest extends SVRRequest {
	
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
