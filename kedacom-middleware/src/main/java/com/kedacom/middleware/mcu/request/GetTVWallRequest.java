package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetTVWallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取电视墙列表
 * @see GetTVWallResponse
 * @author YueZhipeng
 *
 */
public class GetTVWallRequest extends McuRequest {

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("gettvwall");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetTVWallResponse();
	}

}
