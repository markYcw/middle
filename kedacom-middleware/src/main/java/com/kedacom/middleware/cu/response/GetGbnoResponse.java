package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 8.4获取国标Id
 * 
 * @author dengjie
 * ＠see GetGbnoRequest
 * 
 */
public class GetGbnoResponse extends CuResponse {

	private String gbno;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.gbno = jsonData.optString("gbno");
	}

	public String getGbno() {
		return gbno;
	}

	public void setGbno(String gbno) {
		this.gbno = gbno;
	}
}
