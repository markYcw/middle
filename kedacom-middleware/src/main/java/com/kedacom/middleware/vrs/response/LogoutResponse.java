package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出 录播服务器
 * @see LogoutRequest
 * @author LinChaoYu
 *
 */
public class LogoutResponse extends VRSResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 