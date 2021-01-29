package com.kedacom.middleware.gk.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.gk.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登出 GK服务器
 * @see LogoutResponse
 * @author LinChaoYu
 *
 */
public class LogoutRequest extends GKRequest {
	
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
