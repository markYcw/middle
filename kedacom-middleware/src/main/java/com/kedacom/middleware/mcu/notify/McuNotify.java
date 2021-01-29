package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.mcu.request.McuRequest;
import com.kedacom.middleware.mcu.response.McuResponse;
import org.json.JSONObject;

/**
 * 会议平台(Mcu)通知(notify)的父类。
 * @see McuRequest 
 * @see McuResponse
 * @author TaoPeng
 *
 */
public abstract class McuNotify implements INotify {
	
	/**
	 * 会话标识
	 */
	private int ssid;
	private int ssno;
	private int devicetype;
	
	/**
	 * 解析nty节点
	 * @param jsonData
	 */
	protected void parseNty(JSONObject jsonData){
		//@see TCPClientDataReciver onData
		JSONObject req = jsonData.optJSONObject("nty");
		if(req != null){
			this.ssid = req.optInt("ssid");
			this.ssno = req.optInt("ssno");
			this.devicetype = req.optInt("devtype");
		}
	}

	@Override
	public DeviceType getDeviceType() {
		DeviceType devType = DeviceType.parse(devicetype);
		if(devType == null)
			devType = DeviceType.MCU;

		return devType;
	}

	public int getSsid() {
		return ssid;
	}

	public void setSsid(int ssid) {
		this.ssid = ssid;
	}

	public int getSsno() {
		return ssno;
	}

	public void setSsno(int ssno) {
		this.ssno = ssno;
	}

	public int getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(int devicetype) {
		this.devicetype = devicetype;
	}
}
