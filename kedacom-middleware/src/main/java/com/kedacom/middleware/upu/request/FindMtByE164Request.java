package com.kedacom.middleware.upu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.upu.response.FindMtByE164Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 根据终端E164获取终端信息
 * @see FindMtByE164Response
 *  
 * @author LinChaoYu
 *
 */
public class FindMtByE164Request extends UPURequest {
	
	public List<String> e164s;
	
	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("findmtbye164");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		JSONArray array = new JSONArray();
		
		if(e164s != null && e164s.size() > 0){
			for(String e164:e164s){
				array.put(e164);
			}
		}
		
		data.put("e164s", array);
		

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new FindMtByE164Response();
	}

	public List<String> getE164s() {
		return e164s;
	}

	public void setE164s(List<String> e164s) {
		this.e164s = e164s;
	}

}

