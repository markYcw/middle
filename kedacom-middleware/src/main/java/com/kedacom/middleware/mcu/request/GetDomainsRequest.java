package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetDomainsResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取平台域（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class GetDomainsRequest extends McuRequest {

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getdomains");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetDomainsResponse();
	}
}
