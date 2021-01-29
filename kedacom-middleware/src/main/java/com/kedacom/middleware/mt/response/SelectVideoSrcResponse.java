package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.SelectVideoSrcRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：选择视频源.
 * @author LiPengJia
 * @see SelectVideoSrcRequest
 */
public class SelectVideoSrcResponse extends MTResponse {

	/*		{
	“resp”:{ “name”:” selectvideosrc”,
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
