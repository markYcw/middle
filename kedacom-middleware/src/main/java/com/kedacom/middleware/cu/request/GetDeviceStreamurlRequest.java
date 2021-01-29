package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetDeviceStreamurlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取通道播放URL
 * 
 * @author zhanyanan
 * @see GetDeviceStreamurlResponse
 * 
 */
public class GetDeviceStreamurlRequest extends CuRequest{
	private int type=1;
	private String domain;
	private String puid;
	private int chnid;
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getdevicestreamurl");

		// 通道部分
		JSONObject channel = new JSONObject();
		channel.put("puid", this.getPuid());
		channel.put("chnid", this.getChnid());

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("domain", this.domain);
		data.put("channel", channel);
		data.put("type", type);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetDeviceStreamurlResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
