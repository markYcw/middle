package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.StopplatrecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.5 停止平台录像
 * 
 * @see StopplatrecRequest
 * 
 */
public class StopplatrecResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
