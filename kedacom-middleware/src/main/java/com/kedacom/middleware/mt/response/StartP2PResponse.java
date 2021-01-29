package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.request.StartP2PRequest;
import org.json.JSONObject;

/**
 * 
 * 终端响应（Response）：开启点对点会议。
 * @author TaoPeng
 * @see StartP2PRequest
 */
public class StartP2PResponse extends MTResponse {

	/*{
		“resp”:{ “name”:”startp2p”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		}
		}*/

	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
		
	}
	
}
