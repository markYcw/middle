package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartBrstRequest;
import org.json.JSONObject;

/**
 * 开始广播
 * @see StartBrstRequest
 * @author Administrator
 *
 */
public class StartBrstResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
