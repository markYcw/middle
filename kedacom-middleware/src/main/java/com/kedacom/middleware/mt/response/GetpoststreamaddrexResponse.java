package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.domain.Transaddress;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetpoststreamaddrexResponse extends MTResponse {

	private List<Transaddress> transaddrlist = new ArrayList<Transaddress>();
	private Transaddress transaddress = null;
	JSONArray array = null;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		System.out.println(">>"+jsonData.toString());
		array = jsonData.optJSONArray("transaddr");
		if (array != null) {
			for (int i = 0; i < array.length(); i++) {
				transaddress = new Transaddress();
				try {
					transaddress.setIp(array.optJSONObject(i).getString("ip"));
					transaddress.setPort(array.optJSONObject(i).getInt("port"));
					transaddrlist.add(transaddress);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public List<Transaddress> getTransaddrlist() {
		return transaddrlist;
	}

	public void setTransaddrlist(List<Transaddress> transaddrlist) {
		this.transaddrlist = transaddrlist;
	}

}
