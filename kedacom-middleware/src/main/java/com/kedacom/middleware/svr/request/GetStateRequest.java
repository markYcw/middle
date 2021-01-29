package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetStateResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取svr状态
 * @see GetStateResponse
 * @author DengJie
 *
 */
public class GetStateRequest extends SVRRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getstate");
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		
		return new GetStateResponse();
	}

}
