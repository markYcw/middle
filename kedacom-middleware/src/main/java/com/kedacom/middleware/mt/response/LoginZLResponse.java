package com.kedacom.middleware.mt.response;

import org.json.JSONObject;

/**
 * 卓蓝设备 响应（Response）：
 */
public class LoginZLResponse extends MTResponse {
	
	private int sessionid;
	
	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
		this.sessionid = jsonData.optInt("sessionid");
	}

	public int getSessionid() {
		return sessionid;
	}

	public void setSessionid(int sessionid) {
		this.sessionid = sessionid;
	}
}
