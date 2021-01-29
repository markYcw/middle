package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.RecStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class RecStatusRequest extends MTRequest {

	private int devtype;

	private int mode;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("recstatusreq");

		// Data部分
		JSONObject json = new JSONObject();

		json.put("req", req);
		json.put("mode", mode);
		json.put("devtype", devtype);

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new RecStatusResponse();
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
