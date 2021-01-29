package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 取消直播
 * 
 * @author LinChaoYu
 *
 */
public class DelRtspUrlResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}
}
