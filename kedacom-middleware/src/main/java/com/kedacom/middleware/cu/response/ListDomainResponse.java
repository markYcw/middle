package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.domain.Domain;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 3. 获取域列表
 * 
 * @author dengjie
 * 
 */
public class ListDomainResponse extends CuResponse {

	/**
	 * 域ID 域名称 父类域ID
	 */
	private List<Domain> domains = new ArrayList<Domain>();

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		JSONArray domainArr = jsonData.optJSONArray("domains");
		if (domainArr != null && domainArr.length() > 0) {
			for (int i = 0; i < domainArr.length() ; i++) {
				Domain dm = new Domain();
				JSONObject jsonObj = domainArr.optJSONObject(i);
				dm.setDomainid(jsonObj.optString("domainid"));
				dm.setName(jsonObj.optString("name"));
				dm.setParent(jsonObj.optString("parent"));
				this.domains.add(dm);
			}
		}
	}

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

}
