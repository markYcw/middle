package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetConfListResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取视频会议列表（中间件5.0接口）
 * @author LinChaoYu
 *
 */
public class GetConfListRequest extends McuRequest {
	
	/**
	 * 起始序号：从0开始
	 */
	private int start;
	
	/**
	 * 获取会议信息最大个数，0：表示返回所有
	 */
	private int count;

	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getconflist");
		
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("start", this.start);
		data.put("count", this.count);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetConfListResponse();
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
