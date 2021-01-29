package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.LoginMixerResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginMixerRequest extends MTRequest {

	/**
	 * 混音器类型为3
	 */
	private int devtype;

	/**
	 * 类型2
	 */
	private int mode;

	/**
	 * 录像机IP地址
	 */
	private String ip;

	/**
	 * 1录像机；2刻录机；3混音器
	 */
	private int subdevtype;

	/**
	 * 刻录机舱门 <br/>
	 * 1：舱门1；2：舱门2 <br/>
	 * 该字段对kdv2kb无意义
	 */
	private int serverid;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("login");

		// Data部分
		JSONObject json = new JSONObject();

		json.put("req", req);
		json.put("devtype", 3);
		json.put("mode", 2);
		json.put("ip", ip);
		json.put("serverid", serverid);
		json.put("subdevtype", 3);

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new LoginMixerResponse();
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getServerid() {
		return serverid;
	}

	public void setServerid(int serverid) {
		this.serverid = serverid;
	}

	public int getSubdevtype() {
		return subdevtype;
	}

	public void setSubdevtype(int subdevtype) {
		this.subdevtype = subdevtype;
	}

}
