package com.kedacom.middleware.gk.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录 GK服务器
 * @see LoginRequest
 * @author LinChaoYu
 *
 */
public class LoginResponse extends GKResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
		

	}

}
