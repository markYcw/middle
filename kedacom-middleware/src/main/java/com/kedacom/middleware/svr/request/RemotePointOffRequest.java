package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.RemotePointOffResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停用远程点
 * @see RemotePointOffResponse
 * 
 * @author LinChaoYu
 *
 */
public class RemotePointOffRequest extends SVRRequest {
	
	/**
	 * 远程点名称，不能为空，长度限制为32字节（不包含结束符）
	 */
	private String rpname;
	
	/**
	 * IP地址
	 */
	private String ip;
	
	/**
	 * 码率
	 */
	private int rate;
	
	
	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("remotepointoff");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("rpname", rpname);
		data.put("ip", ip);
		data.put("rate", rate);
		
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		
		return new RemotePointOffResponse();
	}
	
	public String getRpname() {
		return rpname;
	}

	public void setRpname(String rpname) {
		this.rpname = rpname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

}
