package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：GetRemotePointVideoSourceRespose
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:47:47
 * @version
 *
 */
public class GetRemotePointVideoSourceRespose extends SVRResponse{
	
	private int outvideochnid;//作为远程点的输出画面通道
	
	private int h323secvideochnid; //启用双流时第二路码流通道
	
	private int remmergestate;//是否将远程点作为合成画面 0:不作为 1作为

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.outvideochnid = jsonData.optInt("outvideochnid");
		this.h323secvideochnid = jsonData.optInt("h323secvideochnid");
		this.remmergestate = jsonData.optInt("remmergestate");
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
