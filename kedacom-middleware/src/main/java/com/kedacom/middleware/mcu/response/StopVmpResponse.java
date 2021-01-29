package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopVmpRequest;
import org.json.JSONObject;

/**
 * 停止画面合成
 * @see StopVmpRequest
 * @author Administrator
 *
 */
public class StopVmpResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
