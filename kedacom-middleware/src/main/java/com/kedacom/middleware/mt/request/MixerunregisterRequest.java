package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.MixerunregisterResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class MixerunregisterRequest extends MTRequest {

	private int mode;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("mixerunregisterreq");

		// Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("mode", mode);

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new MixerunregisterResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
