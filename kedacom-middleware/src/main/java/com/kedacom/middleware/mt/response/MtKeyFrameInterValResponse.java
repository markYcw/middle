package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.MtKeyFrameInterValRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：关键帧间隔设置。
 * @author LiPengJia
 * @see MtKeyFrameInterValRequest
 */
public class MtKeyFrameInterValResponse extends MTResponse {

	/*{
		“resp”:{ “name”:”mtkeyframeInterval”,
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
