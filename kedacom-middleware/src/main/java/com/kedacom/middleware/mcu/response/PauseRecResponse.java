package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.PauseRecRequest;
import org.json.JSONObject;

/**
 * 暂停录像
 * @see PauseRecRequest
 * @author YueZhipeng
 *
 */
public class PauseRecResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
