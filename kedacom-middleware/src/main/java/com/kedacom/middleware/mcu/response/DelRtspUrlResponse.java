package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 取消直播响应结果
 * 
 * @author LinChaoYu
 *
 */
public class DelRtspUrlResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}
}
