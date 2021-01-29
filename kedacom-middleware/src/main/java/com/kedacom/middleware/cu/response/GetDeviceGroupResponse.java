package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.GetDeviceGroupRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 8.1 获取设备组信息
 * 
 * @author dengjie
 * @see GetDeviceGroupRequest
 */
public class GetDeviceGroupResponse extends CuResponse {

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
