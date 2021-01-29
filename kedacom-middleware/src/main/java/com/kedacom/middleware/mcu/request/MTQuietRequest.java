package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.MTQuietResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 单个终端静音/哑音设置
 * @see MTQuietResponse
 * @author YueZhipeng
 */
public class MTQuietRequest extends McuRequest {
	/**
	 * 会议e164号
	 */
	private String confe164;

	/**
	 * 终端IP或者e164号
	 */
	private String mtinfo;
	
	/**
	 * True:静音
     * False:哑音
	 */
	private boolean ismute;
	
	/**
	 * 静音哑音开关
	 */
	private boolean isopen;
	
	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
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
		JSONObject req = super.buildReq("mtquiet");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("ismute", ismute);
		data.put("isopen", isopen);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new MTQuietResponse();
	}

}
