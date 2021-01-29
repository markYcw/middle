package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Inspection;
import com.kedacom.middleware.mcu.request.GetInspectionListRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取终端选看列表(5.0接口)
 * @see GetInspectionListRequest
 * @author LinChaoYu
 * 
 */
public class GetInspectionListResponse extends McuResponse {
	
	private List<Inspection> insList;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		try {
			JSONArray jsons = jsonData.optJSONArray("inspections");
			if(jsons != null){
				 insList = new ArrayList<Inspection>();
				for(int i=0;i<jsons.length();i++){
					Inspection ins = new Inspection();
					
					JSONObject json = jsons.getJSONObject(i);
					ins.setMode(json.optInt("mode"));
					
					JSONObject jsonSrc = json.optJSONObject("src");
					if(jsonSrc != null){
						ins.setSrcType(jsonSrc.optInt("type"));
						ins.setSrcMtId(jsonSrc.getString("mt_id"));
					}
					
					JSONObject jsonDst = json.optJSONObject("dst");
					if(jsonDst != null){
						ins.setDstMtId(jsonDst.getString("mt_id"));
					}
					
					insList.add(ins);
				}
			}
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}
	}

	public List<Inspection> getInsList() {
		return insList;
	}

	public void setInsList(List<Inspection> insList) {
		this.insList = insList;
	}
}
