package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.StopMtStreamRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：停止浏览码流
 * @author LiPengJia
 * @see StopMtStreamRequest
 */
public class StopMtStreamResponse extends MTResponse {

/*	{
		“resp”:{ “name”:” stopmtstream”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		}
		}*/

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
