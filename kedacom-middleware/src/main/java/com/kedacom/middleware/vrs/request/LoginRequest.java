package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录 录播服务器
 * @see LoginResponse
 * @author LinChaoYu
 *
 */
public class LoginRequest extends VRSRequest {

	/**
	 * VRS的IP地址
	 */
	private String ip;
	
	/**
	 * VRS的密码
	 */
	private String user;
	
	/**
	 * VRS的密码
	 */
	private String pwd;

	/**
	 * 设备类型
	 */
	private int devtype = DeviceType.VRS50.getValue();
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("login");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("devtype", devtype);
		data.put("req", req);
		data.put("ip", this.ip);
		data.put("user", this.user);
		data.put("pwd", this.pwd);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new LoginResponse();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
}
