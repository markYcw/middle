package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.SetMTVolumeRequest;
import org.json.JSONObject;

/**
 * 调节音量
 * @see SetMTVolumeRequest
 * @author YueZhipeng
 *
 */
public class SetMTVolumeResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
