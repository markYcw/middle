package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SetRemotePointVideoSourceRespose;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SetRemotePointVideoSourceRequest
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:47:36
 * @version
 *
 */
public class SetRemotePointVideoSourceRequest extends SVRRequest {

	private int outvideochnid;//作为远程点的输出画面通道
	
	private int h323secvideochnid; //启用双流时第二路码流通道
	
	private int remmergestate;//是否将远程点作为合成画面 0:不作为 1作为	
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setremdevmgr");
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("outvideochnid", outvideochnid);
		data.put("h323secvideochnid", h323secvideochnid);
		data.put("remmergestate", remmergestate);
		//返回
		String ret = data.toString();
		return ret;

	}

	@Override
	public IResponse getResponse() {
		return new SetRemotePointVideoSourceRespose();
	}

	public int getOutvideochnid() {
		return outvideochnid;
	}

	public void setOutvideochnid(int outvideochnid) {
		this.outvideochnid = outvideochnid;
	}

	public int getH323secvideochnid() {
		return h323secvideochnid;
	}

	public void setH323secvideochnid(int h323secvideochnid) {
		this.h323secvideochnid = h323secvideochnid;
	}

	public int getRemmergestate() {
		return remmergestate;
	}

	public void setRemmergestate(int remmergestate) {
		this.remmergestate = remmergestate;
	}

}
