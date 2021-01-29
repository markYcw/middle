package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.VolumeCtrlRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：音量控制.
 * @author LiPengJia
 * @see VolumeCtrlRequest
 */
public class VolumeCtrlResponse extends MTResponse {

	/*{
		“resp”:{ “name”:”volumectrl”,
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
