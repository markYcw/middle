package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetRecorderStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 根据录像ID获取录像录制状态（会议平台5.0及以上版本特有）
 * @see GetReocrderStatusResponse
 * @author LinChaoYu
 *
 */
public class GetRecorderStatusRequest extends McuRequest {
	
	/**
	 * 会议e164串号
	 */
	private String confe164;
	
	/**
	 * 录像ID
	 */
	private String recid;

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("getrecstatusbyrecid");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("recid", recid);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetRecorderStatusResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}
}
