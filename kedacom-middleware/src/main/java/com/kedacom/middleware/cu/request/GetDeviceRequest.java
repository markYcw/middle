package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.domain.DeviceStatusSubscribe;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetDeviceResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 8.2 获取设备信息
 * 
 * @author dengjie 
 * @see GetDeviceResponse
 */
public class GetDeviceRequest extends CuRequest {
	/**
	 * 是否只获取本域设备。 True:只获取本域 False:本域，下级
	 */
	private boolean islocal;
	private String groupid;  //分组ID
	
	private DeviceStatusSubscribe deviceStatusSubscribe = new DeviceStatusSubscribe();;//设备状态订阅
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getdevice");

		//subscibetype部分
		JSONObject subscribetype = new JSONObject();
		subscribetype.putOpt("isonline", deviceStatusSubscribe.getSubscribeOnline());
		subscribetype.putOpt("isalarm", deviceStatusSubscribe.getSubscribeAlarm());
		subscribetype.putOpt("isvidchn", deviceStatusSubscribe.getSubscribeChannel());
		subscribetype.putOpt("isgps", deviceStatusSubscribe.getSubscribeGps() );
		subscribetype.putOpt("istvwall", deviceStatusSubscribe.getSubscribeTvwall());
		subscribetype.putOpt("isrecstatus", deviceStatusSubscribe.getSubscribeRecord() );
		subscribetype.putOpt("istransdata", deviceStatusSubscribe.getSubscribeTransdata());

		/*
		 * 设备状态、通道状态、录像状态，总是订阅
		 */
		subscribetype.putOpt("isonline", true);
		subscribetype.putOpt("isvidchn", true);
		subscribetype.putOpt("isrecstatus", true );
		
		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("islocal", islocal);
		data.putOpt("groupid", groupid);
		data.putOpt("issubscibe", true); //获取设备时，总是立即订阅,获取设备不进行订阅
		data.putOpt("subscribetype", subscribetype);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetDeviceResponse();
	}

	public boolean isIslocal() {
		return islocal;
	}

	public void setIslocal(boolean islocal) {
		this.islocal = islocal;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public DeviceStatusSubscribe getDeviceStatusSubscribe() {
		return deviceStatusSubscribe;
	}

	public void setDeviceStatusSubscribe(DeviceStatusSubscribe deviceStatusSubscribe) {
		this.deviceStatusSubscribe = deviceStatusSubscribe;
	}


}
