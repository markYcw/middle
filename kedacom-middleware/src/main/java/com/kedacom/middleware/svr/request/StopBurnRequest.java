package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.StopBurnResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SVR停止刻录
 * @see StopBurnResponse
 * @author DengJie
 *
 */
public class StopBurnRequest extends SVRRequest {
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("stopburn");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		
		return new StopBurnResponse();
	}

}
