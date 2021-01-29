package com.kedacom.middleware.mt.response;

import org.json.JSONObject;

public class TransmitInfoResponse extends MTResponse {

	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
	}
}
