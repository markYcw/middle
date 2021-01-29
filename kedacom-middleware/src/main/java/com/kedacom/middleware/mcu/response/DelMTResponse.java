package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.DelMTRequest;
import org.json.JSONObject;

/**
 * 删除终端
 * @see DelMTRequest
 * @author YueZhipeng
 *
 */
public class DelMTResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
