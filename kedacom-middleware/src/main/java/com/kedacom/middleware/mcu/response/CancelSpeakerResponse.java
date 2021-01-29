package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.CancelSpeakerRequest;
import org.json.JSONObject;

/**
 * 取消发言人
 * @see CancelSpeakerRequest
 * @author YueZhipeng
 *
 */
public class CancelSpeakerResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
