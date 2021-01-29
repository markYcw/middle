package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetTimeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 2. 登出平台
 * 
 * @author dengjie
 * @see GetTimeResponse
 */
public class GetTimeRequest extends CuRequest {

	/**
	 * 是否立即同步本地时间
	 */
	private boolean sync;
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("gettime");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("issyn", sync);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetTimeResponse();
	}

	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}

}
