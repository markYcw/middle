package com.kedacom.middleware.mt.response;

import org.json.JSONObject;

/**
 * 
 * 终端响应（Response）：无返回数据的响应。
 * 例如：在登录、注销后，只需要根据返回数据的错误码来判断是否成功，而没有更多的业务数据返回。
 * @author TaoPeng
 *
 */
public class SimpleResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
	}
	
}
