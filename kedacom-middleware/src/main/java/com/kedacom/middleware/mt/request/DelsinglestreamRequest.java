package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.DelsinglestreamResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class DelsinglestreamRequest extends MTRequest {

	private int type;
	private String ip;
	private int port;

	@Override
	public String toJson() throws JSONException {
		JSONObject req = super.buildReq("delsinglestream");
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		data.put("ip", ip);
		data.put("port", port);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new DelsinglestreamResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
