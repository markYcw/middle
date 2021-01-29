package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.domain.DeviceStatusSubscribe;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.SubScribeStatusResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashSet;

/**
 * 8.3 设备状态订阅(平台2.0接口)
 * 
 * @author dengjie
 * @see SubScribeStatusResponse
 */
public class SubScribeStatusRequest extends CuRequest {

	/**
	 * True表示取消订阅 False 表示订阅
	 */
	private boolean iscancel;
	/**
	 * 订阅的设备ID数组，一次最多20个
	 */
	private HashSet<String> puids = new HashSet<String>();
	

	private DeviceStatusSubscribe deviceStatusSubscribe = new DeviceStatusSubscribe();//设备状态订阅
	

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject subscribestatus = super.buildReq("subscribestatus");
		// subscribetype部分
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
		data.putOpt("req", subscribestatus);
		data.putOpt("iscancel", iscancel);
		data.putOpt("puids", puids);
		data.putOpt("subscribetype", subscribetype);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new SubScribeStatusResponse();
	}

	public boolean isIscancel() {
		return iscancel;
	}

	public void setIscancel(boolean iscancel) {
		this.iscancel = iscancel;
	}

	public void addPuid(String puid){
		this.puids.add(puid);
	}

	public void addPuids(Collection<String> puids){
		this.puids.addAll(puids);
	}
	public DeviceStatusSubscribe getDeviceStatusSubscribe() {
		return deviceStatusSubscribe;
	}

	public void setDeviceStatusSubscribe(DeviceStatusSubscribe deviceStatusSubscribe) {
		this.deviceStatusSubscribe = deviceStatusSubscribe;
	}


}
