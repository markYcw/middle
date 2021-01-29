package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 建立码流交换
 * @see StartStreamexChangeRequest
 * @author LinChaoYu
 *
 */
public class StartStreamexChangeResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
