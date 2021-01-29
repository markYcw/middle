package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.BurnStatusNotify;
import org.json.JSONException;

public class BurnStatusNotifyRequest extends MTRequest{

	@Override
	public String toJson() throws JSONException {
		return null;
	}

	@Override
	public IResponse getResponse() {
		return new BurnStatusNotify();
	}

}
