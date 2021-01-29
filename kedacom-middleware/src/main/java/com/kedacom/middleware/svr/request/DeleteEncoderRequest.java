package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.DeleteEncoderRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：DeleteEncoderRequest
 * 类描述：删除编码器
 * 创建人：lzs
 * 创建时间：2019-7-19 下午5:08:09
 * @version
 *
 */
public class DeleteEncoderRequest extends SVRRequest{
	
	private int chnid;//通道号

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("deleteencchn");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new DeleteEncoderRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
