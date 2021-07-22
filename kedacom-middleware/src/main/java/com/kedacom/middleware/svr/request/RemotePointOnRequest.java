package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.RemotePointOnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

;

/**
 * 开启远程点
 * @see RemotePointOnResponse
 * 
 * @author ycw
 * @date 2021/4/22 11:20
 */
@Data
public class RemotePointOnRequest extends SVRRequest {
	
	/**
	 * 远程点名称，不能为空，长度限制为32字节（不包含结束符）
	 */
	private String rpname;


	/**
	 * 远程点URL
	 * 这个填写了，下面的IP，rate就没用，不解析了。
	 */
	private String url;

	/**
	 * 远程点IP地址
	 */
	private String ip;
	
	/**
	 * 码率
	 */
	private int rate;
	
	/**
	 * 是否开启双流。false表示否
	 */
	private boolean dual = false;
	
	
	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("remotepointon");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("rpname", rpname);
		data.put("url",url);
		data.put("ip", ip);
		data.put("rate", rate);
		data.put("dual", dual);
		
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		
		return new RemotePointOnResponse();
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

	public boolean isDual() {
		return dual;
	}

	public void setDual(boolean dual) {
		this.dual = dual;
	}

}
