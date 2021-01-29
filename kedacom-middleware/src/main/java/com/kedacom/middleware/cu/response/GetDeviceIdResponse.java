package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class GetDeviceIdResponse extends CuResponse{
	private String domain;
	private String puid;
	private int chn;
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.domain = jsonData.optString("domain");
		this.puid = jsonData.optString("puid");
		this.chn = jsonData.optInt("chn");
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPuid() {
		return puid;
	}
	public void setPuid(String puid) {
		this.puid = puid;
	}
	public int getChn() {
		return chn;
	}
	public void setChn(int chn) {
		this.chn = chn;
	}
	
}
