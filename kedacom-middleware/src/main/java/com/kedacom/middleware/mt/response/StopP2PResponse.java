package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.request.StopP2PRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：停止点对点会议。
 * @author LiPengJia
 * @see StopP2PRequest
 */
public class StopP2PResponse extends MTResponse {
/*
	{
		“resp”:{ “name”:”stopp2p”,
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
