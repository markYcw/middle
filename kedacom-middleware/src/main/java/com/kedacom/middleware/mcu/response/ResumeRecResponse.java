package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.ResumeRecRequest;
import org.json.JSONObject;

/**
 * 11)恢复录像
 * @see ResumeRecRequest
 * @author YueZhipeng
 *
 */
public class ResumeRecResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
	}

}
