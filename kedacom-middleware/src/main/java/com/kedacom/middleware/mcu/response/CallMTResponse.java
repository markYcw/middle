package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.CallMTRequest;
import org.json.JSONObject;

/**
 * 呼叫终端
 * @see CallMTRequest
 * @author YueZhipeng
 *
 */
public class CallMTResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
