package com.kedacom.middleware.gk.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.gk.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录 GK服务器
 * @see LoginResponse
 * @author LinChaoYu
 *
 */
public class LoginRequest extends GKRequest {

	/**
	 * GK的IP地址
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
	 * 是否为管理员
	 */
	private int isadmin;
	/**
	 * 设备类型
	 */
	private int devtype = DeviceType.GK.getValue();
	
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
		data.put("port", this.port);
		data.put("isadmin", this.isadmin);
		
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

	public int getIsadmin() {
		return isadmin;
	}

	public void setIsadmin(int isadmin) {
		this.isadmin = isadmin;
	}
}
