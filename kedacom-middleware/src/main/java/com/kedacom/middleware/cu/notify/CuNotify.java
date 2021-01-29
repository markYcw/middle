package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.cu.request.CuRequest;
import com.kedacom.middleware.cu.response.CuResponse;
import org.json.JSONObject;

/**
 * 监控平台(Cu)的“通知(Notify)”。
 * @see CuRequest
 * @see CuResponse
 * @author TaoPeng
 *
 */
public abstract class CuNotify implements INotify {
	
	/**
	 * 流水号
	 */
	private int ssno;
	
	/**
	 * 会话标识
	 */
	private int ssid;
	/**
	 * 解析resp节点
	 * @param jsonData
	 */
	protected void parseNty(JSONObject jsonData){
		JSONObject req = jsonData.optJSONObject("nty");
		if(req != null){
			this.ssno = req.optInt("ssno");
			this.ssid = req.optInt("ssid");
		}
	}
	
	@Override
	public DeviceType getDeviceType() {
		return DeviceType.CU;
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
