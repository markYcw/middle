package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartPartmixRequest;
import org.json.JSONObject;

/**
 * 定制混音
 * @see StartPartmixRequest
 * @author YueZhipeng
 *
 */
public class StartPartmixResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
