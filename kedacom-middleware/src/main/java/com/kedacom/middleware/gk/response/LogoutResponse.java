package com.kedacom.middleware.gk.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出 GK服务器
 * @see LogoutRequest
 * @author LinChaoYu
 *
 */
public class LogoutResponse extends GKResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 