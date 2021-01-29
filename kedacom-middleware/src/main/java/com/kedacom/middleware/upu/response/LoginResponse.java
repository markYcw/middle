package com.kedacom.middleware.upu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.upu.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录UPU服务器
 * @see LoginRequest
 * 
 * @author LinChaoYu
 *
 */
public class LoginResponse extends UPUResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
