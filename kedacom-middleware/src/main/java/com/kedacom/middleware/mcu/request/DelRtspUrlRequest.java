package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.DelRtspUrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 取消直播
 * 
 * @author LinChaoYu
 *
 */
public class DelRtspUrlRequest extends McuRequest{
	
	/**
	 * 会议平台类型：DeviceType.MCU 或 DeviceType.MCU5
	 */
	private int devtype;
	
	/**
	 * 直播路径
	 */
	private String url;
	
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("delrtspurl");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", devtype);
		json.put("url", url);
		
		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new DelRtspUrlResponse();
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
