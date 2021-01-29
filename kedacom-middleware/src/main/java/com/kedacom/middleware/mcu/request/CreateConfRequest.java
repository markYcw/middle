package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.domain.Confinfo;
import com.kedacom.middleware.mcu.response.CreateConfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建会议/模板
 * @see CreateConfResponse
 * @author YueZhipeng
 *
 */
public class CreateConfRequest extends McuRequest {
    
	/**
	 * 1：即时会议2：会议模板
	 */
	private int mode;
	
	private Confinfo confinfo;
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Confinfo getConfinfo() {
		return confinfo;
	}

	public void setConfinfo(Confinfo confinfo) {
		this.confinfo = confinfo;
	}

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("createconf");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("mode", mode);
		data.put("confinfo", new JSONObject(confinfo));
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new CreateConfResponse();
	}

}
