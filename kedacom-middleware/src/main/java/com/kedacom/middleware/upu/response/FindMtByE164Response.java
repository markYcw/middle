package com.kedacom.middleware.upu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.upu.domain.UPUMt;
import keda.common.util.ToolsUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据终端标识获取终端信息
 * 
 * @author LinChaoYu
 *
 */
public class FindMtByE164Response extends UPUResponse {

	private List<UPUMt> mts;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		
		JSONArray jsonMts = jsonData.optJSONArray("mts");
		if(jsonMts != null){
			mts = new ArrayList<UPUMt>();
			
			for(int i = 0 ; i < jsonMts.length(); i ++){
				try{
					JSONObject obj = jsonMts.getJSONObject(i);
					
					int status = 0;
					if(!obj.isNull("state")){
						String state = obj.optString("state");
						if(!ToolsUtil.isEmpty(state)){
							status = 1;
						}
					}
					
					String e164 = obj.optString("e164");

					UPUMt mt = new UPUMt();
					mt.setE164(e164);
					mt.setStatus(status);
				
					mts.add(mt);
				}catch(JSONException e){
					throw new DataException(e.getMessage(), e);
				}
			}
		}
	}

	public List<UPUMt> getMts() {
		return mts;
	}

	public void setMts(List<UPUMt> mts) {
		this.mts = mts;
	}
}
