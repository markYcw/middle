package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.request.LoginRequest;
import org.json.JSONObject;

/**
 * 
 * 终端响应（Response）：
 * @author TaoPeng
 * @see LoginRequest
 */
public class LoginResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
	}
	
}
