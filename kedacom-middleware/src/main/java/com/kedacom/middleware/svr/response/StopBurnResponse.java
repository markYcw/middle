package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.StopBurnRequest;
import org.json.JSONObject;

;

/**
 * SVR停止刻录
 * @see StopBurnRequest
 * @author DengJie
 *
 */
public class StopBurnResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 