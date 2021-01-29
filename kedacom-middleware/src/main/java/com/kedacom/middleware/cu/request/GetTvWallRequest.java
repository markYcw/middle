package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetTvWallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3. 获取电视墙回复，电视墙详情由nty上报
 * 
 * @author fanshaocong
 */
public class GetTvWallRequest extends CuRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("gettvwall");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetTvWallResponse();
	}
}
