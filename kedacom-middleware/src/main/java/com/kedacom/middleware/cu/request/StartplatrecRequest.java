package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StartplatrecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 10.4 开启平台录像
 * 
 * @author dengjie
 * @see StartplatrecResponse
 */
public class StartplatrecRequest extends CuRequest {


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
		JSONObject req = super.buildReq("startplatrec");
		
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
		return new StartplatrecResponse();
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
