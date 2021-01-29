package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetDecoderMainAndAuxiliaryRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：GetDecoderMainAndauxiliaryRequest
 * 类描述：获取解码器的解码通道和主辅流
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:51:48
 * @version
 *
 */
public class GetDecoderMainAndAuxiliaryRequest extends SVRRequest{
	
	private int chnid;//通道号

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getdecitem");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new GetDecoderMainAndAuxiliaryRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
