package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SearchEncoderAndDecoderRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SearchEncoderAndDecoderRequest
 * 类描述：搜索编码器和解码器
 * 创建人：lzs
 * 创建时间：2019-8-20 上午9:12:35
 * @version
 *
 */
public class SearchEncoderAndDecoderRequest extends SVRRequest{

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("discodev");
		JSONObject data = new JSONObject();
		data.put("req", req);
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new SearchEncoderAndDecoderRespose();
	}

}
