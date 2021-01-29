package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

/**
 * 获取与会成员
 * @author YueZhipeng
 *
 */
public class GetJoinEdmtResponse extends McuResponse {
    
	/**
	 * 终端IP或者e164号数组
	 */
	private Set<String> mtinfos;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
       
		Set<String> set = new HashSet<String>();
		JSONArray mtinfos = jsonData.optJSONArray("mtinfos");
		for(int i = 0 ; i < mtinfos.length(); i ++){
			try{
				String mt = mtinfos.getString(i);
				set.add(mt);
			}catch(JSONException e){
				throw new DataException(e.getMessage(), e);
			}
		}
		this.mtinfos = set;
	}

	public Set<String> getMtinfos() {
		return mtinfos;
	}

	public void setMtinfos(Set<String> mtinfos) {
		this.mtinfos = mtinfos;
	}

}
