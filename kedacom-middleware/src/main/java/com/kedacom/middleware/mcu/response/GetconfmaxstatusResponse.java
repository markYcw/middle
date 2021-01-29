package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GetconfmaxstatusResponse extends McuResponse {

	private int mixmode;
	private int mtnum;
	private String confe164;
	private List<String> mtinfo;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		System.out.println(">>" + jsonData);
		super.parseResp(jsonData);
		this.mixmode = jsonData.optInt("mixmode");
		this.mtnum = jsonData.optInt("mtnum");
		this.confe164 = jsonData.optString("confe164");
		JSONArray array = jsonData.optJSONArray("mtinfo");
		try {
			if(array != null){
				for (int i = 0; i < array.length(); i++) {
					mtinfo.add(array.getString(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public int getMixmode() {
		return mixmode;
	}

	public void setMixmode(int mixmode) {
		this.mixmode = mixmode;
	}

	public int getMtnum() {
		return mtnum;
	}

	public void setMtnum(int mtnum) {
		this.mtnum = mtnum;
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public List<String> getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(List<String> mtinfo) {
		this.mtinfo = mtinfo;
	}

}
