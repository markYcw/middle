package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.StartpurecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.6 开启前端录像
 * 
 * @see StartpurecRequest
 * 
 * @author dengjie
 * 
 */
public class StartpurecResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
