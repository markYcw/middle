package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetconfvmpstatusResponse extends McuResponse {

	private int vmpauto;
	private int vmpbrdst;
	private int vmpstyle;
	private int vmpschemeid;
	private int vmpmode;
	private int rimenabled;
	private String cone164;
	private List<String> mtinfo;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		JSONArray array = new JSONArray();
		mtinfo = new ArrayList<String>();
		this.vmpauto = jsonData.optInt("vmpauto");
		this.vmpbrdst = jsonData.optInt("vmpbrdst");
		this.vmpstyle = jsonData.optInt("vmpstyle");
		this.vmpschemeid = jsonData.optInt("vmpschemeid");
		this.vmpmode = jsonData.optInt("vmpmode");
		this.rimenabled = jsonData.optInt("rimenabled");
		this.cone164 = jsonData.optString("cone164");
		array = jsonData.optJSONArray("mtinfo");
		try {
			if(array != null){
				for (int i = 0; i < array.length(); i++) {
					String str = (String) array.get(i);
					mtinfo.add(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getVmpauto() {
		return vmpauto;
	}

	public void setVmpauto(int vmpauto) {
		this.vmpauto = vmpauto;
	}

	public int getVmpbrdst() {
		return vmpbrdst;
	}

	public void setVmpbrdst(int vmpbrdst) {
		this.vmpbrdst = vmpbrdst;
	}

	public int getVmpstyle() {
		return vmpstyle;
	}

	public void setVmpstyle(int vmpstyle) {
		this.vmpstyle = vmpstyle;
	}

	public int getVmpschemeid() {
		return vmpschemeid;
	}

	public void setVmpschemeid(int vmpschemeid) {
		this.vmpschemeid = vmpschemeid;
	}

	public int getVmpmode() {
		return vmpmode;
	}

	public void setVmpmode(int vmpmode) {
		this.vmpmode = vmpmode;
	}

	public int getRimenabled() {
		return rimenabled;
	}

	public void setRimenabled(int rimenabled) {
		this.rimenabled = rimenabled;
	}

	public String getCone164() {
		return cone164;
	}

	public void setCone164(String cone164) {
		this.cone164 = cone164;
	}

	public List<String> getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(List<String> mtinfo) {
		this.mtinfo = mtinfo;
	}

}
