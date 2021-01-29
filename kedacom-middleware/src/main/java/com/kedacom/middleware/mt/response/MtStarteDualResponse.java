package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.MtStarteDualRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：双流发送控制
 * @author LiPengJia
 * @see MtStarteDualRequest
 */
public class MtStarteDualResponse extends MTResponse {

	/*{
		“resp”:{ “name”:” mtstartedual”,
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
