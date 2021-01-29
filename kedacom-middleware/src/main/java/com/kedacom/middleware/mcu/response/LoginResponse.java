package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录平台
 * @see LoginRequest
 * @author TaoPeng
 *
 */
public class LoginResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
		

	}

}
