package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.DelMixMemberRequest;
import org.json.JSONObject;

/**
 * 删除混音成员
 * @see DelMixMemberRequest
 * @author YueZhipeng
 *
 */
public class DelMixMemberResponse extends McuResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

	}

}
