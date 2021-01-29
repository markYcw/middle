package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetRecorderStatusListResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取录像录制状态列表（会议平台5.0及以上版本特有）
 * @see GetReocrderStatusListResponse
 * @author LinChaoYu
 *
 */
public class GetRecorderStatusListRequest extends McuRequest {
	
	/**
	 * 会议e164串号
	 */
	private String confe164;

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("getrecstatusbyconf");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetRecorderStatusListResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}
}
