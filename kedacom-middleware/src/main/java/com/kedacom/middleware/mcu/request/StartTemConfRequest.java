package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartTemConfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开启模板会议
 * @see StartTemConfResponse
 * @author YueZhipeng
 *
 */
public class StartTemConfRequest extends McuRequest {
	
	/**
	 * 创建时填写的
	 */
	private String e164;
	
	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	@Override
	public String toJson() throws JSONException {
		
		//req部分
		JSONObject req = super.buildReq("starttemconf");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
	    data.put("e164", e164);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartTemConfResponse();
	}

}
