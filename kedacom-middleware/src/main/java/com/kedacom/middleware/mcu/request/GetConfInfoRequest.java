package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetConfInfoResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取会议信息
 * @see GetConfInfoResponse
 * @author YueZhipeng
 *
 */
public class GetConfInfoRequest extends McuRequest {//javadoc
    
	/**
	 * 会议e164串号
	 */
	private String e164;
	
	/**
	 * 1：即时会议 2：会议模板
	 */
	private int mode;
	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getconfinfo");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("e164", e164);
		data.put("mode", mode);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetConfInfoResponse();
	}

}
