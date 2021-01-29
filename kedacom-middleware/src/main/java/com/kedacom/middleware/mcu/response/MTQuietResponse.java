package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.MTQuietRequest;
import org.json.JSONObject;

/**
 * 单个终端静音/哑音设置
 * @see MTQuietRequest
 * @author YueZhipeng
 */
public class MTQuietResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
