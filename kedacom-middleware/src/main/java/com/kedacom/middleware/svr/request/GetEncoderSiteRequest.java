package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetEncoderSiteRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：GetEncoderSiteRequest
 * 类描述：获取编码器的预置位
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:21:16
 * @version
 *
 */
public class GetEncoderSiteRequest extends SVRRequest{

	private int chnid;//通道
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getipcitem");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new GetEncoderSiteRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
