package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 2. 获取平台域信息
 * 
 * @author dengjie
 * 
 */
public class GetLocalDomainResponse extends CuResponse {

	/**
	 * 域ID
	 */
	private String domainid;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.domainid = jsonData.optString("domainid");

	}

	public String getDomainid() {
		return domainid;
	}

	public void setDomainid(String domainid) {
		this.domainid = domainid;
	}

}
