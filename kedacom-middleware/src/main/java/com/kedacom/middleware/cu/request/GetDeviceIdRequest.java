package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetDeviceIdResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 国标ID获取2.0 PUID
 * 
 * @author zhangyanan
 * @see GetDeviceIdResponse
 */
public class GetDeviceIdRequest extends CuRequest{
	/**
	 * 国标 ID
	 */
	private String gbno;
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getdeviceid");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("gbno", gbno);
		
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetDeviceIdResponse();
	}

	public String getGbno() {
		return gbno;
	}

	public void setGbno(String gbno) {
		this.gbno = gbno;
	}

}
