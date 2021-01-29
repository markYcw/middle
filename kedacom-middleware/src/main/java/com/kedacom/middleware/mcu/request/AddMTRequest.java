package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.AddMTResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加终端
 * @see AddMTResponse
 * @author YueZhipeng
 *
 */
public class AddMTRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 终端IP或者e164号
	 */
	private String mtinfo;
	
	/**
	 * （5.0接口有效）呼叫标识类型：5-e164号码; 6-电话; 7-ip地址
	 */
	private int type;
	
	/**
	 * 终端呼叫码率
	 */
	private int callrate;
	
	/**
	 * 呼叫模式，0：手动，2：定时
	 */
	private int callmode;
	
	/**
	 * （5.0接口有效）是否强拆：0-否、1-是
	 */
	private int forced_call = -1;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCallrate() {
		return callrate;
	}

	public void setCallrate(int callrate) {
		this.callrate = callrate;
	}

	public int getCallmode() {
		return callmode;
	}

	public void setCallmode(int callmode) {
		this.callmode = callmode;
	}

	public int getForced_call() {
		return forced_call;
	}

	public void setForced_call(int forced_call) {
		this.forced_call = forced_call;
	}

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("addmt");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("callrate", callrate);
		data.put("callmode", callmode);
		if(this.type != 0)
			data.put("type", this.type);
		if(this.forced_call != -1)
			data.put("forced_call", this.forced_call);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new AddMTResponse();
	}

}
