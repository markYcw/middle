package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.DiskInfoResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询磁阵信息
 * @see DiskInfoResponse
 * @author hyb
 *
 */
public class DiskInfoRequest extends CuRequest {
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getdiskinfo");
		
		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);

	    // 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new DiskInfoResponse();
	}
}
