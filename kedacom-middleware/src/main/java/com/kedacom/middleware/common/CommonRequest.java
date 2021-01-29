package com.kedacom.middleware.common;

import com.kedacom.middleware.client.IRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @see CommonResponse
 * @author TaoPeng
 *
 */
public abstract class CommonRequest implements IRequest {

	/**
	 * 流水号
	 */
	private int ssno;
	
	/**
	 * 会话标识
	 */
	private int ssid;
	
	@Override
	public void setSsno(int ssno) {
		this.ssno = ssno;

	}

	/**
	 * 生成消息的req部分
	 * @param name
	 * @return
	 * @throws JSONException
	 */
	protected JSONObject buildReq(String name) throws JSONException {
		JSONObject req = new JSONObject();
		req.putOpt("name", name);
		req.putOpt("ssno", this.getSsno());
		req.putOpt("ssid", this.getSsid());
		return req;
		
	}
	

	public int getSsno() {
		return ssno;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

}
