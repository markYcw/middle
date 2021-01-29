package com.kedacom.middleware.common;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 申请会话
 * @see GetSSIDRequest
 * @author TaoPeng
 *
 */
public class GetSSIDResponse extends CommonResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		this.parseResp(jsonData);
	}

}
