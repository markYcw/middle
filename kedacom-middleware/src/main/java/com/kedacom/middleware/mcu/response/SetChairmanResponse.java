package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.SetChairmanRequest;
import org.json.JSONObject;

/**
 * 设置主席
 * @see SetChairmanRequest
 * @author YueZhipeng
 *
 */
public class SetChairmanResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
