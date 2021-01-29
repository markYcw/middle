package com.kedacom.middleware.gk.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.gk.response.GetRegentityListResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取已注册GK实体列表
 *  @see GetRegentityListResponse
 * @author LinChaoYu
 *
 */
public class GetRegentityListRequest extends GKRequest {
	
	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("getregentitylist");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetRegentityListResponse();
	}

}

