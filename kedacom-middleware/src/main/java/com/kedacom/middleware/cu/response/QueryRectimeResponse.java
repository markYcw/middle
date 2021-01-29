package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.domain.Times;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 10.3 录像时段查询
 * 
 * @author dengjie
 * 
 */
public class QueryRectimeResponse extends CuResponse {
	/**
	 * 时间段
	 */
	private List<Times> times = new ArrayList<Times>();

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		JSONArray timeArr = jsonData.optJSONArray("times");
		if (timeArr != null && timeArr.length() > 0) {
			for (int i = 0; i < timeArr.length(); i++) {
				Times time = new Times();
				JSONObject jsonObj = timeArr.optJSONObject(i);
				time.setStarttime(jsonObj.optString("start"));
				time.setEndtime(jsonObj.optString("end"));
				this.times.add(time);
			}
		}
	}

	public List<Times> getTimes() {
		return times;
	}

}
