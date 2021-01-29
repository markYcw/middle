package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopRecRequest;
import org.json.JSONObject;

/**
 * 停止录像
 * @see StopRecRequest
 * @author YueZhipeng
 * 
 */
public class StopRecResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
