package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.request.EndMultiConfRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：退出多点会议。
 * @author LinChaoYu
 * @see EndMultiConfRequest
 */
public class EndMultiConfResponse extends MTResponse {
/*
	{
		“resp”:{ “name”:”endmulticonf”,
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
