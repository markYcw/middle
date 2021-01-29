package com.kedacom.middleware.mt.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 信令透传通知
 * @author wbh
 *
 */
public class TransmitInfoNotify extends MTNotify {


	public static final String NAME = "recvmsg";
	
	private String msg;


	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		this.msg = jsonData.optString("msg");
		
	}
}
