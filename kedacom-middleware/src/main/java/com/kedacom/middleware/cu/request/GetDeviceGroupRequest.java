package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetDeviceGroupResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 8.1 获取设备组信息
 * 
 * @author dengjie
 * @see GetDeviceGroupResponse
 */
public class GetDeviceGroupRequest extends CuRequest {

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getdevicegroup");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetDeviceGroupResponse();
	}

}
