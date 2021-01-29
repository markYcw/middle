package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 
 * 终端响应（Response）：
 * @author TaoPeng
 * @see LogoutRequest
 */
public class LogoutResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
	}
	
}
