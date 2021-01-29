package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopMTMonitorRequest;
import org.json.JSONObject;

/**
 * 停止浏览终端码流
 * @see StopMTMonitorRequest
 * @author YueZhipeng
 */
public class StopMTMonitorResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
