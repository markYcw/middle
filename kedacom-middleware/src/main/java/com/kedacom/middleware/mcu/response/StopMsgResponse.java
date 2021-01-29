package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 停止发送短消息（字幕）
 * @see StopMsgRequest
 * @author LinChaoYu
 *
 */
public class StopMsgResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
