package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.AddMixMemberRequest;
import org.json.JSONObject;

/**
 * 添加混音成员
 * @see AddMixMemberRequest
 * @author YueZhipeng
 *
 */
public class AddMixMemberResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
