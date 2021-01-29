package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.DelTvWallResponse;
import com.kedacom.middleware.cu.response.ListDomainResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3. 获取域列表
 * 
 * @author dengjie
 * @see ListDomainResponse
 */
public class DelTvWallRequest extends CuRequest {
	
	//电视墙ID
	private String id;
	
	//电视墙名称
	private String name;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("deltvwall");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("id", id);
		data.putOpt("name", name);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new DelTvWallResponse();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
