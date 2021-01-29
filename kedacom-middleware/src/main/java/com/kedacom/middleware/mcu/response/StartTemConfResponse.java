package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartTemConfRequest;
import org.json.JSONObject;

/**
 * 开启模板会议
 * @see StartTemConfRequest
 * @author YueZhipeng
 *
 */
public class StartTemConfResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
