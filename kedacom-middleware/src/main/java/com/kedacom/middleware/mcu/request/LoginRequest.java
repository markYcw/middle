package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.LoginResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录平台
 * @see LoginResponse
 * @author TaoPeng
 * 
 * 2016-12-29 LinChaoYu 适配会议平台5.0及以上版本
 *
 */
public class LoginRequest extends McuRequest {

	/**
	 * mcu的IP地址
	 */
	private String ip;
	
	/**
	 * mcu端口
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
	 * （5.0接口有效）SDK调用许可证:帐号
	 */
	private String key;
	
	/**
	 * （5.0接口有效）SDK调用许可证:密码
	 */
	private String secret;
	
	/**
	 * 设备类型（4.7接口和5.0接口传的值不一样）
	 */
	private int devtype = DeviceType.MCU.getValue();
	
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
		
		if(this.key != null && this.key.length() > 0)
			data.put("key", this.key);
		
		if(this.secret != null && this.secret.length() > 0)
			data.put("secret", secret);
		
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

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}
}
