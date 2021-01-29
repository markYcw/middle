package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.RemotePointOnRequest;
import org.json.JSONObject;

;

/**
 * 开启远程点响应结果
 * @see RemotePointOnRequest
 * 
 * @author LinChaoYu
 *
 */
public class RemotePointOnResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 