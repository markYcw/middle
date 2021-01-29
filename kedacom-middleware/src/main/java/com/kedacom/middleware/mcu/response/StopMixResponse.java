package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopMixRequest;
import org.json.JSONObject;

/**
 * 停止混音
 * @see StopMixRequest
 * @author YueZhipeng
 *
 */
public class StopMixResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
