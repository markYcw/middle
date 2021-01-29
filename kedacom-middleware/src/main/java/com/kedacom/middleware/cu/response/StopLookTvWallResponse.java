package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 2. 停止选看电视墙回复StartLookTvWallResponse.java
 * 
 * @author gaoguang
 * 
 */
public class StopLookTvWallResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}
}
