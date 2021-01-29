package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.ListDomainResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3. 获取域列表
 * 
 * @author dengjie
 * @see ListDomainResponse
 */
public class ListDomainRequest extends CuRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("listdomain");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new ListDomainResponse();
	}
}
