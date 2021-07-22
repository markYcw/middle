package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.LoginResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录 录播服务器
 * @see LoginResponse
 * @author LinChaoYu
 *
 */
@Data
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

	/**
	 * 端口
	 */
	private int port;
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("login");
		
		//data部分
		JSONObject data = new JSONObject();
		if(devtype==18){
			data.put("port",port);
		}
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

}
