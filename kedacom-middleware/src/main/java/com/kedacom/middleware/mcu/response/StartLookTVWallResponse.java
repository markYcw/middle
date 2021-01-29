package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartLookTVWallRequest;
import org.json.JSONObject;

/**
 * 终端开始上墙
 * @see StartLookTVWallRequest
 * @author YueZhipeng
 *
 */
public class StartLookTVWallResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
	}

}
