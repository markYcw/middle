package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.domain.Capsupportex;
import com.kedacom.middleware.mt.domain.Doublepayload;
import com.kedacom.middleware.mt.domain.Mixstart;
import com.kedacom.middleware.mt.response.MixerStartResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class MixerStartRequest extends MTRequest {

	// 刻录机类型
	private int mode;

	private Mixstart mixerstart;

	private Doublepayload doublepayload;

	private Capsupportex capsupportex;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("mixerstartreq");

		// Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("mode", mode);
		json.put("mixstart", new JSONObject(mixerstart));
		json.put("doublepayload", new JSONObject(doublepayload));
		json.put("capsupportex", new JSONObject(capsupportex));

		// 返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new MixerStartResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public Mixstart getMixerstart() {
		return mixerstart;
	}

	public void setMixerstart(Mixstart mixerstart) {
		this.mixerstart = mixerstart;
	}

	public Doublepayload getDoublepayload() {
		return doublepayload;
	}

	public void setDoublepayload(Doublepayload doublepayload) {
		this.doublepayload = doublepayload;
	}

	public Capsupportex getCapsupportex() {
		return capsupportex;
	}

	public void setCapsupportex(Capsupportex capsupportex) {
		this.capsupportex = capsupportex;
	}

}
