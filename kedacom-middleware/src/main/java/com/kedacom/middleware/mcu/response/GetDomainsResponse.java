package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.McuDomain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取平台域（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class GetDomainsResponse extends McuResponse {

	private List<McuDomain> domains = new ArrayList<McuDomain>();
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		try {
			
			if(!jsonData.isNull("domains")){
				JSONArray jsons = jsonData.optJSONArray("domains");
				
				if(jsons != null){
					for(int i=0;i<jsons.length();i++){
						McuDomain domain = new McuDomain();
						
						JSONObject json = jsons.getJSONObject(i);
						
						domain.setMoid(json.optString("moid"));
						domain.setParentMoid(json.optString("parent_moid"));
						domain.setName(json.optString("name"));
						domain.setType(json.optString("type"));
						
						this.domains.add(domain);
					}
				}
			}
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}
	}

	public List<McuDomain> getDomains() {
		return domains;
	}

	public void setDomains(List<McuDomain> domains) {
		this.domains = domains;
	}
}
