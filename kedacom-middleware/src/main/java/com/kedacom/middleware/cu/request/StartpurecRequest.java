package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StartpurecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 10.6 开启前端录像
 * 
 * @author dengjie
 * @see StartpurecResponse
 */
public class StartpurecRequest extends CuRequest {

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
		JSONObject req = super.buildReq("startpurec");
		
		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", puid);
		channel.putOpt("chnid", chnid);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("channel", channel);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StartpurecResponse();
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
