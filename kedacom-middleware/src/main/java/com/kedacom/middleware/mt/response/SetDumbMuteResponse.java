package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.SetDumbMuteRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：静音哑音设置。
 * @author LiPengJia
 * @see SetDumbMuteRequest
 */
public class SetDumbMuteResponse extends MTResponse {

	/*{
		“resp”:{ “name”:” setdumbmute”,
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
