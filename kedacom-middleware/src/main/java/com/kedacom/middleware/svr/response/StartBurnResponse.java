package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.StartBurnRequest;
import org.json.JSONObject;

;

/**
 * SVR刻录
 * @see StartBurnRequest
 * @author DengJie
 *
 */
public class StartBurnResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 