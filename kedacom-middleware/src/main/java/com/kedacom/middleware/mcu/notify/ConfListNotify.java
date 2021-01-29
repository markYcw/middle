package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 会议列表通知（4.7中间件-发生变更时触发）
 * 
 * @author LinChaoYu
 *
 */
public class ConfListNotify extends McuNotify {

	public static final String NAME = "conflistnty";
	
	private int mode;
	private List<String> confe164s;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
		int mode = jsonData.optInt("mode");
		List<String> list = new ArrayList<String>();
		JSONArray confe164s = jsonData.optJSONArray("confe164s");
		if(confe164s != null){
			for(int i = 0 ; i < confe164s.length(); i ++){
				try{
					JSONObject obj = confe164s.optJSONObject(i);
					if(obj != null){
						String e164 = obj.optString("e164");
						list.add(e164);
					}
				}catch(Exception e){
					throw new DataException(e.getMessage(), e);
				}
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

	public List<String> getConfe164s() {
		return confe164s;
	}

	public void setConfe164s(List<String> confe164s) {
		this.confe164s = confe164s;
	}
}
