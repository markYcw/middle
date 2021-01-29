package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 停止码流交换
 * @see StopStreamexChangeRequest
 * @author LinChaoYu
 *
 */
public class StopStreamexChangeResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
