package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.DelMTResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 删除终端
 * @see DelMTResponse
 * @author YueZhipeng
 *
 */
public class DelMTRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 终端IP或者e164号
	 */
	private String mtinfo;
	
	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("delmt");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new DelMTResponse();
	}

}
