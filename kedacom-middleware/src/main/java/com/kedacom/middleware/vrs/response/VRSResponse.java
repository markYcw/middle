package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.request.VRSRequest;
import org.json.JSONObject;

/**
 * 录播服务器服务器 响应(response)的父类。
 * @see VRSRequest
 * @author LinChaoYu
 *
 */
public abstract class VRSResponse implements IResponse {
	
	/**
	 * 消息流水号
	 */
	private int ssno;
	
	/**
	 * 会话标识
	 */
	private int ssid;
	
	/**
	 * 错误码。0表示无错误，其它值表示有错误
	 */
	private int errorcode;

	/**
	 * 解析resp节点
	 * @param jsonData
	 */
	protected void parseResp(JSONObject jsonData){
		JSONObject req = jsonData.optJSONObject("resp");
		if(req != null){
			this.ssno = req.optInt("ssno");
			this.ssid = req.optInt("ssid");
			this.errorcode = req.optInt("errorcode");
		}
	}
	
	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public int getSsno() {
		return ssno;
	}

	public void setSsno(int ssno) {
		this.ssno = ssno;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}
	
}
