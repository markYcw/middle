package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SetEncoderSiteRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SetEncoderSiteRequest
 * 类描述：设置编码器的预置位
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:21:28
 * @version
 *
 */
public class SetEncoderSiteRequest extends SVRRequest {

	private int chnid;//通道
	
	private int preset;//预置位
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setipcitem");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		data.put("preset", preset);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new SetEncoderSiteRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

	public int getPreset() {
		return preset;
	}

	public void setPreset(int preset) {
		this.preset = preset;
	}

}
