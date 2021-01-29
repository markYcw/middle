package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录 SVR服务器
 * @see LoginRequest
 * @author DengJie
 *
 */
public class LoginResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
		

	}

}
