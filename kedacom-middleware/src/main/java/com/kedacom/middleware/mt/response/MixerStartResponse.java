package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.Transaddress;
import org.json.JSONException;
import org.json.JSONObject;

public class MixerStartResponse extends MTResponse {

	private Transaddress transaddress;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		transaddress = new Transaddress();
		try {
			JSONObject obj = jsonData.getJSONObject("transaddress");
			transaddress.setIp(obj.getString("ip"));
			transaddress.setPort(obj.getInt("port"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public Transaddress getTransaddress() {
		return transaddress;
	}

	public void setTransaddress(Transaddress transaddress) {
		this.transaddress = transaddress;
	}

}
