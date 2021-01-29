package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetRtspUrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发布直播
 * 
 * @author LinChaoYu
 *
 */
public class GetRtspUrlRequest extends MTRequest{

	/**
	 * 设备类型
	 */
	private int devtype;
	
	/**
	 * 终端IP
	 */
	private String ip;
	
	/**
	 * 终端端口
	 */
	private int port;
	
	/**
	 * 登录帐号
	 */
	private String user;
	
	/**
	 * 登录密码
	 */
	private String pwd;
	
	/**
	 * 1:本地音视频\2:远端音视频\3:本地双流\4: 远端双流
	 */
	private int type;
	
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getrtspurl");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", devtype);
		json.put("ip", ip);
		json.put("port", port);
		json.put("user", user);
		json.put("pwd", pwd);
		json.put("type", type);
		
		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new GetRtspUrlResponse();
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
