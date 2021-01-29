package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetVcrStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取录像机状态列表（会议平台4.7版本特有）
 * @see GetVcrStatusResponse
 * @author YueZhipeng
 *
 */
public class GetVcrStatusRequest extends McuRequest {

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("getvcrstatus");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetVcrStatusResponse();
	}

}
