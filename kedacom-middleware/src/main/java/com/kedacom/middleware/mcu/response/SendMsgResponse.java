package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 发送短消息（字幕）
 * @see StendMsgRequest
 * @author LinChaoYu
 *
 */
public class SendMsgResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
         super.parseResp(jsonData);
	}

}
