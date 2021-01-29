package com.kedacom.middleware.common;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.IResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 申请会话
 * @see GetSSIDResponse
 * @author TaoPeng
 *
 */
public class GetSSIDRequest extends CommonRequest {

	/**
	 * 设备类型
	 */
	private DeviceType deviceType;
	
	@Override
	public String toJson() throws JSONException {

		JSONObject req = super.buildReq("getssid");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("devtype", deviceType.getValue());
		
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetSSIDResponse();
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

}
