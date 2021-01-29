package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出 SVR服务器
 * @see LogoutRequest
 * @author DengJie
 *
 */
public class LogoutResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 