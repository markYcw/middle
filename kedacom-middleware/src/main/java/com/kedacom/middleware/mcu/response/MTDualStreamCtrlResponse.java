package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.MTDualStreamCtrlRequest;
import org.json.JSONObject;

/**
 * 终端双流发送控制
 * @see MTDualStreamCtrlRequest
 * @author YueZhipeng
 *
 */
public class MTDualStreamCtrlResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
