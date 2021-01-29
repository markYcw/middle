package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.RemotePointOffRequest;
import org.json.JSONObject;

;

/**
 * 停用远程点响应结果
 * @see RemotePointOffRequest
 * 
 * @author LinChaoYu
 *
 */
public class RemotePointOffResponse extends SVRResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
 