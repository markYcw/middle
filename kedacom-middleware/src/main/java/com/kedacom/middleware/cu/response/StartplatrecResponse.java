package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.StartplatrecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.4 开启平台录像
 * 
 * @author dengjie
 * @see StartplatrecRequest
 * 
 */
public class StartplatrecResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
