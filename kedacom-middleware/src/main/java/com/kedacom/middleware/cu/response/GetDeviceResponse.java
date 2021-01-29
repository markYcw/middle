package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 8.2 获取设备信息
 * 
 * @author dengjie
 * 
 */
public class GetDeviceResponse extends CuResponse {

	/**
	 * 分组个数，应答只报个数，具体分组信息由nty报。监控平台2.0没有这个值
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
