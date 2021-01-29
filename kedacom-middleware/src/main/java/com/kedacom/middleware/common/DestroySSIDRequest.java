package com.kedacom.middleware.common;

import com.kedacom.middleware.client.IResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 释放会话
 * @see DestroySSIDResponse
 * @author TaoPeng
 *
 */
public class DestroySSIDRequest extends CommonRequest {

	@Override
	public String toJson() throws JSONException {

		JSONObject req = super.buildReq("destroyssid");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new DestroySSIDResponse();
	}

}
