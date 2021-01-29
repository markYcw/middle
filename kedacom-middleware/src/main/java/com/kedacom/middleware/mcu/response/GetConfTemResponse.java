package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Confe164;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取会议/模板列表
 * @author TaoPeng
 *
 */
public class GetConfTemResponse extends McuResponse {

	/**
	 * １：即时会议； 2会议模板
	 */
	private int mode;
	private List<Confe164> confe164s;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseResp(jsonData);
		
		int mode = jsonData.optInt("mode");
		List<Confe164> list = new ArrayList<Confe164>();
		JSONArray confe164s = jsonData.optJSONArray("confe164s");
		for(int i = 0 ; i < confe164s.length(); i ++){
			try{
				JSONObject obj = confe164s.getJSONObject(i);
				String e164 = obj.optString("e164");
				String name = obj.optString("name");
				Confe164 confee164 = new Confe164();
				confee164.setE164(e164);
				confee164.setName(name);
				list.add(confee164);
			}catch(JSONException e){
				throw new DataException(e.getMessage(), e);
			}
		}
		
		this.mode = mode;
		this.confe164s = list;
		
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public List<Confe164> getConfe164s() {
		return confe164s;
	}
	public void setConfe164s(List<Confe164> confe164s) {
		this.confe164s = confe164s;
	}

}
