package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.DelRtspUrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 取消直播
 * 
 * @author LinChaoYu
 *
 */
public class DelRtspUrlRequest extends MTRequest{

	/**
	 * 设备类型
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
