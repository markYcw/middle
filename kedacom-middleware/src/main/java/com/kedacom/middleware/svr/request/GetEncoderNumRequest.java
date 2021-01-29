package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetEncoderNumRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：GetEncoderNumRequest
 * 类描述：获取编码器能力
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:21:57
 * @version
 *
 */
public class GetEncoderNumRequest extends SVRRequest {

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getencdevcap");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new GetEncoderNumRespose();
	}



}
