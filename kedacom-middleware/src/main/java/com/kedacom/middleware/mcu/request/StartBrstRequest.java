package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StartBrstResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开始广播
 * @see StartBrstResponse
 * @author Administrator
 *
 */
public class StartBrstRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 广播源
	 * 0-发言人
	 * 1-画面合成
	 * 2-混音
	 */
	private int src;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startbrst");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("src", src);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartBrstResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

}
