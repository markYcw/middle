package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 8.5获取kdmno(2.0 设备号转uoi设备号)
 * 
 * @author dengjie
 * 
 */
public class GetkdmnoResponse extends CuResponse {

	private String kdmno;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.kdmno = jsonData.optString("kdmno");
	}

	public String getKdmno() {
		return kdmno;
	}

	public void setKdmno(String kdmno) {
		this.kdmno = kdmno;
	}

}
