package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录 录播服务器
 * @see LoginRequest
 * @author LinChaoYu
 *
 */
public class LoginResponse extends VRSResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
		

	}

}
