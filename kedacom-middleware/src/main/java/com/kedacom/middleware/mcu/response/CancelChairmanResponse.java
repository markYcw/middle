package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.CancelChairmanRequest;
import org.json.JSONObject;

/**
 * 取消主席
 * @see CancelChairmanRequest
 * @author YueZhipeng
 *
 */
public class CancelChairmanResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
