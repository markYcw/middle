package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetkdmnoResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 8.5获取kdmno(2.0 设备号转uoi设备号)
 * 
 * @author dengjie
 * @see GetkdmnoResponse
 */
public class GetkdmnoRequest extends CuRequest {

	/**
	 * 设备号
	 */
	private String puid;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getkdmno");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("puid", puid);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetkdmnoResponse();
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

}
