package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 10.2 录像日历
 * 
 * @author dengjie
 * 
 */
public class QueryRecdaysResponse extends CuResponse {

	private int[] recflags;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		JSONArray jsonArray = jsonData.optJSONArray("recflags");
		if (jsonArray != null) {
			int arrLength = jsonArray.length();
			recflags = new int[arrLength];
			if (arrLength > 0) {
				for (int i = 0; i < arrLength; i++) {
					recflags[i] = jsonArray.optInt(i);
				}

			}
		}
	}

	public int[] getRecflags() {
		return recflags;
	}

	public void setRecflags(int[] recflags) {
		this.recflags = recflags;
	}

}
