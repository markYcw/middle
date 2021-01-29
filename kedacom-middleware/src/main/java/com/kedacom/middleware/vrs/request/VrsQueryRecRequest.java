package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.VrsQueryRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 录像查询
 *  @see GetRegentityListResponse
 * @author LinChaoYu
 *
 */
public class VrsQueryRecRequest extends VRSRequest {
	
	/**
	 * 每页的大小
	 */
	private int pagesize;
	
	/**
	 * 查询的页码（从1开始）
	 */
	private int pagenum;
	
	/**
	 * 模糊匹配的录像名字
	 */
	private String includename;
	
	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("vrsqueryrec");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		data.put("pagesize", pagesize);
		data.put("pagenum", pagenum);
		data.put("includename", includename);
		

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new VrsQueryRecResponse();
	}

	public String getIncludename() {
		return includename;
	}

	public void setIncludename(String includename) {
		this.includename = includename;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPagenum() {
		return pagenum;
	}

	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
}

