package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StopLookTVWallRequest;
import org.json.JSONObject;

/**
 * 终端停止上墙
 * @see StopLookTVWallRequest
 * @author YueZhipeng
 *
 */
public class StopLookTVWallResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
