package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IRequest;
import com.kedacom.middleware.svr.response.SVRResponse;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * SVR服务 请求(request)的父类。
 * @see SVRResponse
 * @author DengJie
 *
 */
public abstract class SVRRequest implements IRequest {

	/**
	 * 消息流水号
	 */
	private int ssno;
	
	/**
	 * 会话标识
	 */
	private int ssid;

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
