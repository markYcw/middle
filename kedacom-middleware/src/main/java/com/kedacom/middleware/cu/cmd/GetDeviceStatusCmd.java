package com.kedacom.middleware.cu.cmd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 8.4 获取设备状态(平台1.0)
 * 
 * @author dengjie
 * @deprecated 监控平台1.0专有的特性暂不支持
 * 
 */
public class GetDeviceStatusCmd extends CuCommand {

	/**
	 * 命令值
	 */
	public static final String NAME = "getdevicestatus";
	/**
	 * 设备串号
	 */
	private String puid;

	@Override
	public String toJson() throws JSONException {
		// cmd部分
		JSONObject cmd = super.buildCmd(NAME);
		// Data部分
		JSONObject json = new JSONObject();
		json.put("cmd", cmd);

		// 返回
		String str = json.toString();
		return str;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

}
