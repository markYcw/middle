package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 5. 获取平台时间
 * 
 * @author dengjie
 * 
 */
public class GetTimeNotify extends CuNotify {

	/**
	 * 命令值
	 */
	public static final String NAME = "gettime";
	/**
	 * 平台时间,格式(yyyymmddhhmmss)
	 */
	private long time;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		this.time = jsonData.optLong("time");
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
