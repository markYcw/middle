package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.AddMTRequest;
import org.json.JSONObject;

/**
 * 添加终端
 * @see AddMTRequest
 * @author YueZhipeng
 *
 */
public class AddMTResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
