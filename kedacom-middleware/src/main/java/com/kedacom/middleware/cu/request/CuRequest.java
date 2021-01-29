package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.client.IRequest;
import com.kedacom.middleware.cu.response.CuResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 监控平台(Cu)请求(request)的父类。
 * @see CuResponse
 * @author TaoPeng
 *
 */
public abstract class CuRequest implements IRequest {

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

	@Override
	public abstract CuResponse getResponse();
	
	public int getSsno() {
		return ssno;
	}

	/**
	 * 设置流水号，一般底层会自动设备，调用者不用感知。
	 * @param ssno
	 */
	 @Override
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
