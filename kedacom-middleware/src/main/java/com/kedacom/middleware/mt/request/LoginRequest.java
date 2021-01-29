package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Request：终端：
 * @see LoginResponse
 * @author TaoPeng
 * 
 */
public class LoginRequest extends MTRequest {

	private String ip;
	private int port;
	private String username;
	private String password;
	
	//4.7版本时 DeviceType.MT
	//5.0版本时 DeviceType.MT5
	private int devtype = DeviceType.MT.getValue();

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("login");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", devtype);
		json.put("ip", ip);
		json.put("port", port);
		json.put("user", username);
		json.put("pwd", password);
		
		//返回
		String str = json.toString();
		return str;
		
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
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getDevtype() {
		return devtype;
	}
	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}
}
