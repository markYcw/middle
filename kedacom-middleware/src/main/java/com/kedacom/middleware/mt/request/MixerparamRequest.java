package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.MixerparamResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class MixerparamRequest extends MTRequest {

	private int mode;

	private String ip;

	private int port;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("mixerparamreq");

		// Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("mode", mode);
		json.put("ip", ip);
		json.put("port", port);

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new MixerparamResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
