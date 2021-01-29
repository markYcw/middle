package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetLocalMtListResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取本级会议终端列表信息(5.0接口)
 * @see GetLocalMtListResponse
 * @author LinChaoYu
 *
 */
public class GetLocalMtListRequest extends McuRequest {
    
	/**
	 * 会议e164串号
	 */
	private String confe164;

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getlocalmtlist");
		
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
		return new GetLocalMtListResponse();
	}

}
