package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartVmpRequest;
import org.json.JSONObject;

/**
 * 开始画面合成
 * @see StartVmpRequest
 * @author YueZhipeng
 *
 */
public class StartVmpResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
