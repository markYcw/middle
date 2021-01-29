package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.ReleaseConfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 结束会议、删除模板
 * @see ReleaseConfResponse
 * @author YueZhipeng
 *
 */
public class ReleaseConfRequest extends McuRequest {
    
	/**
	 * 创建时填写的
	 */
	private String e164;
	
	/**
	 * 1：即时会议
     * 2：会议模板
	 */
	private int mode;
	
	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	@Override
	public String toJson() throws JSONException {

		//req部分
	    JSONObject req = super.buildReq("releaseconf");
	    
	    //data部分
	    JSONObject data = new JSONObject();
	    data.put("req",req);
	    data.put("e164", e164);
	    data.put("mode", mode);
	    
	    //返回
	    String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new ReleaseConfResponse();
	}

}
