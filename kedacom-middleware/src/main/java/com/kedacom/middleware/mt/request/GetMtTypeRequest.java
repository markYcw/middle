package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetMtTypeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Request：终端：
 * @author TaoPeng
 * @see GetMtTypeResponse
 */
public class GetMtTypeRequest extends MTRequest {

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("getmttype");

		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);

		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new GetMtTypeResponse();
	}

}
