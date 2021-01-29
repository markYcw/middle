package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 9.5 停止语音呼叫(未实现)
 * 
 * @author dengjie
 * 
 */
public class StopAudioCallResponse extends CuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
