package com.kedacom.middleware.common;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 释放会话
 * @see DestroySSIDRequest
 * @author TaoPeng
 *
 */
public class DestroySSIDResponse extends CommonResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}
	

}
