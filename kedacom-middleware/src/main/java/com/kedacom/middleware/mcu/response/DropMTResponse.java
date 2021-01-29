package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.DropMTRequest;
import org.json.JSONObject;

/**
 * 挂断终端
 * @see DropMTRequest
 * @author YueZhipeng
 *
 */
public class DropMTResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
