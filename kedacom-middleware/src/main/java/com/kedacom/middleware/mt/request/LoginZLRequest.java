package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.LoginZLResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 卓蓝设备登录请求
 * @author cys
 * 
 */
public class LoginZLRequest extends MTRequest {

	private String ip;
	private int port;
	private int subdevtype;

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("login");
		req.remove("ssid");//卓兰请求头没有ssid
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", DeviceType.CUPBOARD.getValue());
		json.put("subdevtype", subdevtype);
		json.put("ip", ip);
		json.put("port", port);
		
		//返回
		String str = json.toString();
		return str;
		
	}
	@Override
	public IResponse getResponse() {
		return new LoginZLResponse();
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
	public int getSubdevtype() {
		return subdevtype;
	}
	public void setSubdevtype(int subdevtype) {
		this.subdevtype = subdevtype;
	}
}
