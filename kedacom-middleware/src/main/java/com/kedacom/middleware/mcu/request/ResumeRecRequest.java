package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.ResumeRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 恢复录像
 * @see ResumeRecResponse
 * @author YueZhipeng
 *
 */
public class ResumeRecRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;
	/**
	 * 终端e164或 IP
	 */
	private String mtinfo;
	/**
	 * 是否是会议录像 False: 终端录像
	 */

	private boolean isconf;

	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("resumerec");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("isconf", isconf);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new ResumeRecResponse();
	}

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

	public boolean isIsconf() {
		return isconf;
	}

	public void setIsconf(boolean isconf) {
		this.isconf = isconf;
	}

}
