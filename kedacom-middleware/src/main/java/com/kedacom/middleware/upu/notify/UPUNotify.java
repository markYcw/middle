package com.kedacom.middleware.upu.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.gk.request.GKRequest;
import com.kedacom.middleware.gk.response.GKResponse;
import org.json.JSONObject;

/**
 * UPU服务 通知(notify)的父类。
 * @see GKRequest 
 * @see GKResponse
 * @author LinChaoYu
 *
 */
public abstract class UPUNotify implements INotify {
	
	/**
	 * 会话标识
	 */
	private int ssid;
	private int ssno;
	
	/**
	 * 解析nty节点
	 * @param jsonData
	 */
	protected void parseNty(JSONObject jsonData){
		JSONObject req = jsonData.optJSONObject("nty");
		if(req != null){
			this.ssid = req.optInt("ssid");
			this.ssno = req.optInt("ssno");
		}
	}

	@Override
	public DeviceType getDeviceType() {
		return DeviceType.GK;
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
