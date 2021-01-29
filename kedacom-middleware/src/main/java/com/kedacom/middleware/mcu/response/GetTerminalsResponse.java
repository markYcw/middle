package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Terminal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取平台注册终端列表（会议平台5.0及以上版本特有）
 * 
 * @author LinChaoYu
 *
 */
public class GetTerminalsResponse extends McuResponse {

	/**
	 * 当前分页终端列表
	 */
	private List<Terminal> terminals = new ArrayList<Terminal>();
	
	/**
	 * 终端总数
	 */
	private int total;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		try {
			
			if(!jsonData.isNull("total_count")){
				this.total = jsonData.optInt("total_count");
			}
			
			if(!jsonData.isNull("terminals")){
				JSONArray jsons = jsonData.optJSONArray("terminals");
				
				if(jsons != null){
					for(int i=0;i<jsons.length();i++){
						Terminal terminal = new Terminal();
						
						JSONObject json = jsons.getJSONObject(i);
						
						terminal.setDomainMoid(json.optString("domain_moid"));
						terminal.setMoid(json.optString("moid"));
						terminal.setName(json.optString("name"));
						terminal.setE164(json.optString("e164"));
						terminal.setOnline(json.optString("online"));
						
						this.terminals.add(terminal);
					}
				}
			}
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}
	}

	public List<Terminal> getTerminals() {
		return terminals;
	}

	public void setTerminals(List<Terminal> terminals) {
		this.terminals = terminals;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
