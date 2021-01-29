package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.PipSwitchRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：画面切换。
 * @author LiPengJia
 * @see PipSwitchRequest
 */
public class PipSwitchResponse extends MTResponse {

	/*{
	“resp”:{ “name”:”pipswitch”,
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
