package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopBrstRequest;
import org.json.JSONObject;

/**
 * 停止广播
 * @see StopBrstRequest
 * @author YueZhipeng
 *
 */
public class StopBrstResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
