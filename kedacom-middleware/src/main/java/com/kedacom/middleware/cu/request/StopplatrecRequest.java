package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StopplatrecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 10.5 停止平台录像
 * 
 * @author dengjie
 * @see StopplatrecResponse
 */
public class StopplatrecRequest extends CuRequest {

	/**
	 * 域
	 */
	private String domain;

	/**
	 * 通道信息
	 */
	private String puid;
	/**
	 * 通道信息 chnid
	 */
	private int chnid;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("stopplatrec");

		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", puid);
		channel.putOpt("chnid", chnid);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("domain", domain);
		data.putOpt("channel", channel);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StopplatrecResponse();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
