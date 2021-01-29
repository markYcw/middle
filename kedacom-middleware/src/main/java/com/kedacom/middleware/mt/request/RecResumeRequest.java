package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.RecResumeResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class RecResumeRequest extends MTRequest {

	private int mode;

	@Override
	public String toJson() throws JSONException {

		// Req部分
		JSONObject req = super.buildReq("recresumereq");

		// Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("mode", 2);// VRS2KB(2)

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		// TODO Auto-generated method stub
		return new RecResumeResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
