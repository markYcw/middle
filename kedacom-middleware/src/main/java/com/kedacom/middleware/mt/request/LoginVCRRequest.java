package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.LoginVCRResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginVCRRequest extends MTRequest {

	private String ip;
	private int mode;
	private int devtype;
	private int subdevtype; //1为录像机；2为刻录机

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("login");

		// Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", 3);
		json.put("ip", ip);
		json.put("mode", mode);
		json.put("subdevtype", 1);
		
		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new LoginVCRResponse();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public int getSubdevtype() {
		return subdevtype;
	}

	public void setSubdevtype(int subdevtype) {
		this.subdevtype = subdevtype;
	}

}
