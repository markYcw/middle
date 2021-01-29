package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetConfTemResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取会议/模板列表
 * @author YueZhipeng
 *
 */
public class GetConfTemRequest extends McuRequest {
    
	/**
	 * １：即时会议； 2会议模板
	 */
	private int mode;
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getconftem");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("mode", mode);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetConfTemResponse();
	}

}
