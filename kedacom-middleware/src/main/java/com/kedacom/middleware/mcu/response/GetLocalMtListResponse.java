package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Mts;
import com.kedacom.middleware.mcu.request.GetLocalMtListRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取本级会议终端列表信息
 * @see GetLocalMtListRequest
 * @author LinChaoYu
 * 
 */
public class GetLocalMtListResponse extends McuResponse {
	private List<Mts> mtsList = new ArrayList<Mts>();

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		try {
			
			if(!jsonData.isNull("mts")){
				JSONArray jsons = jsonData.optJSONArray("mts");
				
				if(jsons != null){
					for(int i=0;i<jsons.length();i++){
						Mts mts = new Mts();
						
						JSONObject json = jsons.getJSONObject(i);
						
						mts.setCalled(json.optInt("online"));
						mts.setIp(json.optString("ip"));
						mts.setMtId(json.optString("mt_id"));
						mts.setE164(json.optString("e164"));
						mts.setRate(json.optInt("bitrate"));
						mts.setDual(json.optInt("dual"));
						mts.setMix(json.optInt("mix"));
						mts.setVmp(json.optInt("vmp"));
						mts.setInspection(json.optInt("inspection"));
						mts.setPoll(json.optInt("poll"));
						mts.setMute(json.optInt("mute"));
						mts.setSilence(json.optInt("silence"));
						mts.setCall_mode(json.optInt("call_mode"));
						mts.setProtocol(json.optInt("protocol"));
						mts.setRec(json.optInt("rec"));
						mts.setAlias(json.optString("alias"));
						
						mtsList.add(mts);
					}
				}
			}
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}
	}

	public List<Mts> getMtsList() {
		return mtsList;
	}

	public void setMtsList(List<Mts> mtsList) {
		this.mtsList = mtsList;
	}
}
