package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class LoginMixerResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
	}

}
