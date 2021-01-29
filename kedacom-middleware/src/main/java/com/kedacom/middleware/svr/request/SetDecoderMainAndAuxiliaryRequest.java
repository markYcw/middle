package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SetDecoderMainAndAuxiliaryRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SetDecoderMainAndauxiliaryRequest
 * 类描述：设置解码器的解码通道和主辅流
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:52:03
 * @version
 *
 */
public class SetDecoderMainAndAuxiliaryRequest extends SVRRequest{
	
	private int chnid;//通道号

    private int encchnid;//编码器通道号

    private int secstream;//解码主辅流(0主流，1辅流)

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setdecitem");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		data.put("encchnid", encchnid);
		data.put("secstream", secstream);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new SetDecoderMainAndAuxiliaryRespose();
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

	public int getEncchnid() {
		return encchnid;
	}

	public void setEncchnid(int encchnid) {
		this.encchnid = encchnid;
	}

	public int getSecstream() {
		return secstream;
	}

	public void setSecstream(int secstream) {
		this.secstream = secstream;
	}

}
