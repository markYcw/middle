package com.kedacom.middleware.vrs.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.vrs.request.VRSRequest;
import com.kedacom.middleware.vrs.response.VRSResponse;
import org.json.JSONObject;

/**
 * 录播服务器 通知(notify)的父类。
 * @see VRSRequest 
 * @see VRSResponse
 * @author LinChaoYu
 *
 */
public abstract class VRSNotify implements INotify {
	
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
			devType = DeviceType.VRS50;
		
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
}
