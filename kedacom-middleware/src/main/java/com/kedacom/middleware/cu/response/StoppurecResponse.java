package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.StoppurecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.7 停止前端录像
 * 
 * @see StoppurecRequest
 * 
 */
public class StoppurecResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
