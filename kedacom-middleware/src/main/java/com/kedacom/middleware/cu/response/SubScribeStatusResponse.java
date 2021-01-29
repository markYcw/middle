package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 8.3 设备状态订阅(平台2.0接口)
 * 
 * @author dengjie
 * 
 */
public class SubScribeStatusResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
