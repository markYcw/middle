package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.GetDumbMuteRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：静音哑音状态获取。
 * @author LiPengJia
 * @see GetDumbMuteRequest
 */
public class GetDumbMuteResponse extends MTResponse {

	/*
	 * { “resp”:{ “name”:” getdumbmute”, “ssno”:1, “ssid”:5， “errorcode”:0 } }
	 */
	private String mute;

	private String open;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		mute = jsonData.optString("ismute");
		open = jsonData.optString("isopen");
	}

	public GetDumbMuteResponse() {
	}

	public GetDumbMuteResponse(String mute, String open) {
		this.mute = mute;
		this.open = open;
	}

	public String getMute() {
		return mute;
	}

	public void setMute(String mute) {
		this.mute = mute;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}
