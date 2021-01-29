package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 10.1 获取录像
 * 
 * @author dengjie
 * 
 */
public class GetRecordResponse extends CuResponse {
	/**
	 * 总数目(2.0没这个)
	 */
	private int num;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.num = jsonData.optInt("num");

	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
