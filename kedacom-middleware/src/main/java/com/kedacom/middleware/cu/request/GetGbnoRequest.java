package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetGbnoResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 8.4获取国标ID
 * 
 * @author dengjie
 * @see GetGbnoResponse
 */
public class GetGbnoRequest extends CuRequest {

	/**
	 * 域号
	 */
	private String domain;
	/**
	 * 设备号
	 */
	private String puid;
	/**
	 * 通道号
	 */
	private int chn;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getgbno");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("domain", domain);
		data.putOpt("puid", puid);
		data.putOpt("chn", chn);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetGbnoResponse();
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getChn() {
		return chn;
	}

	public void setChn(int chn) {
		this.chn = chn;
	}

}
