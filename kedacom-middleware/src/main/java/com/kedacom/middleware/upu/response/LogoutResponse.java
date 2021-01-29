package com.kedacom.middleware.upu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.upu.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出 UPU服务器
 * @see LogoutRequest
 * 
 * @author LinChaoYu
 *
 */
public class LogoutResponse extends UPUResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 