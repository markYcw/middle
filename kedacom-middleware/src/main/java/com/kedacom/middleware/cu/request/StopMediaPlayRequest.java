package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StopMediaPlayResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 9.2 停止浏览视频(未实现)
 * 
 * @author dengjie
 * @see StopMediaPlayResponse
 */
public class StopMediaPlayRequest extends CuRequest {

	/**
	 * 监控通道号， 播放时返回的值
	 */
	private int index;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("stopmediaplay");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("index", index);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StopMediaPlayResponse();
	}

}
