package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.PtzCtrlRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：Ptz控制.
 * @author ycw
 * @Date 2021/4/2
 * @see PtzCtrlRequest
 */
public class PtzCtrlResponse extends MTResponse {


	@Override
	public void parseData(JSONObject jsonData) throws DataException {

		super.parseResp(jsonData);

	}

}
