package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetOldTerminalsResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取三代高清终端列表(非受管)（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class GetOldTerminalsRequest extends McuRequest {
	
	/**
	 * 获取终端列表的起始位置, 0表示第一个终端, 默认为0
	 */
	private int start;
	
	/**
	 * 获取的终端的个数, 即包括start在内的后count个终端
	 */
	private int count;

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getoldterminals");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		data.put("start", start);
		data.put("count", count);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetOldTerminalsResponse();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
