package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.AllMTQuietResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 全场终端静音/哑音设置
 * @see AllMTQuietResponse
 * @author YueZhipeng
 *
 */
public class AllMTQuietRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * True:静音
	 * False:哑音
	 */
	private boolean ismute;

	/**
	 * 静音/哑音是否开启
	 */
	private boolean isopen;
	
	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public boolean isIsmute() {
		return ismute;
	}

	public void setIsmute(boolean ismute) {
		this.ismute = ismute;
	}

	public boolean isIsopen() {
		return isopen;
	}

	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}

	@Override
	public String toJson() throws JSONException {

		// Req部分
		JSONObject req = super.buildReq("allmtquiet");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("ismute", ismute);
		data.put("isopen", isopen);
		
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new AllMTQuietResponse();
	}

}
