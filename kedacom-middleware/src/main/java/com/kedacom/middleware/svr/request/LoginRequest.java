package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录 SVR服务器
 * @see LoginResponse
 * @author DengJie
 *
 */
public class LoginRequest extends SVRRequest {

	/**
	 * SVR的IP地址
	 */
	private String ip;
	
	/**
	 * 端口
	 */
	private int port;
	
	/**
	 * 用户名
	 */
	private String user;
	
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 设备类型
	 */
	private int devtype = DeviceType.SVR.getValue();
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("login");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("devtype", devtype);
		data.put("req", req);
		data.put("ip", this.ip);
		data.put("port", this.port);
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

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
