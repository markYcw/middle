package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.ReleaseConfRequest;
import org.json.JSONObject;

/**
 * 结束会议、删除模板
 * @see ReleaseConfRequest
 * @author YueZhipeng
 *
 */
public class ReleaseConfResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
