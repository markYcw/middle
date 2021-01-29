package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.DeleteDecoderRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：DeleteDecoderRequest
 * 类描述：删除解码器
 * 创建人：lzs
 * 创建时间：2019-7-19 下午5:07:00
 * @version
 *
 */
public class DeleteDecoderRequest extends SVRRequest {
	
	private int chnid;//通道号

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("deletedecchn");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new DeleteDecoderRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
