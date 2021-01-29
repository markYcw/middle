package com.kedacom.middleware.common;

import com.kedacom.middleware.client.IResponse;
import org.json.JSONObject;

/**
 * 
 * @see CommonRequest
 * @author TaoPeng
 *
 */
public abstract class CommonResponse implements IResponse {

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
