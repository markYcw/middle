package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.SetSpeakerRequest;
import org.json.JSONObject;

/**
 * 设置发言人
 * @see SetSpeakerRequest
 * @author YueZhipeng
 *
 */
public class SetSpeakerResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
