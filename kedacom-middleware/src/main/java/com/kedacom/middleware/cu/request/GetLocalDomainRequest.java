package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetLocalDomainResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 2. 获取平台域信息
 * 
 * @author dengjie
 * @see GetLocalDomainResponse
 * 
 */
public class GetLocalDomainRequest extends CuRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getlocaldomain");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetLocalDomainResponse();
	}

}
