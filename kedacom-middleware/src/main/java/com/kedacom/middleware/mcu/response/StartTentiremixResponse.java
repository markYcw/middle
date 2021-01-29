package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartTentiremixRequest;
import org.json.JSONObject;

/**
 * 智能混音
 * @see StartTentiremixRequest
 * @author YueZhipeng
 * 
 */
public class StartTentiremixResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
