package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.AllMTQuietRequest;
import org.json.JSONObject;

/**
 * 全场终端静音/哑音设置
 * @see AllMTQuietRequest
 * @author YueZhipeng
 *
 */
public class AllMTQuietResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
