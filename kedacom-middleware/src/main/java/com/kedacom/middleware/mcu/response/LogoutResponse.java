package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出平台
 * @see LogoutRequest
 * @author YueZhipeng
 *
 */
public class LogoutResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 