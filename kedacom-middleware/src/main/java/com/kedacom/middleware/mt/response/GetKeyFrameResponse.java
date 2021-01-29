package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.GetKeyFrameRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：请求关键帧。
 * @author LiPengJia
 * @see GetKeyFrameRequest
 */
public class GetKeyFrameResponse extends MTResponse {

	/*{
		“resp”:{ “name”:” getkeyframe”,
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
