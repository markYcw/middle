package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.VoiceActiveRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：VoiceActiveRequest
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-9-24 下午4:13:56
 * @version
 *
 */
public class VoiceActiveRequest extends SVRRequest {
	
	private boolean  isnty;//是否开启语音激励状态 ，全局生效

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setaudioactnty");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("isnty", isnty);
		
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new VoiceActiveRespose();
	}

	public boolean isIsnty() {
		return isnty;
	}

	public void setIsnty(boolean isnty) {
		this.isnty = isnty;
	}

}
