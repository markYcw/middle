package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetpoststreamaddrexResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class GetpoststreamaddrexRequest extends MTRequest {

	/**
	 * type 码流类型
		0: 本地
		1：远端
		2:本地视频到任意地址
		3:远端视频到任意地址
		4:本地音频到任意地址
		5:远端音频到任意地址
		6:本地双流到任意地址
		7:远端双流到任意地址
	 */
	private int type;
	
	@Override
	public String toJson() throws JSONException {
		JSONObject req = super.buildReq("getpoststreamaddrex");
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetpoststreamaddrexResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
