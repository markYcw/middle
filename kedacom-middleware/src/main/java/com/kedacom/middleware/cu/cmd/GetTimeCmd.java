package com.kedacom.middleware.cu.cmd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取平台时间
 * @author TaoPeng
 *
 */
public class GetTimeCmd extends CuCommand {

	@Override
	public String toJson() throws JSONException {
		//cmd部分
		JSONObject cmd = super.buildCmd("gettime");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("cmd", cmd);
		
		//返回
		String str = json.toString();
		return str;
	}


}
