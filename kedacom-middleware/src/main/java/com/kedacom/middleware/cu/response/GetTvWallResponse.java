package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 2. 获取电视墙返回
 * 
 * @author gaoguang
 * 
 */
public class GetTvWallResponse extends CuResponse {

	/**
	 * 电视墙id
	 */
	private Integer num;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.num = jsonData.optInt("num");
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
