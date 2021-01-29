package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.TransmitInfoResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class TransmitInfoRequest extends MTRequest  {
	
	private String msg;


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("sendmsg");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("msg", msg);
		
		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new TransmitInfoResponse();
	}


}
