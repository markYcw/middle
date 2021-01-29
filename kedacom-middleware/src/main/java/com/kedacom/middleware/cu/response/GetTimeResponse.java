package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.cu.request.GetTimeRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 5. 获取平台时间
 * 
 * @author dengjie
 * @see GetTimeRequest
 * 
 */
public class GetTimeResponse extends CuResponse {

	/**
	 * 命令值
	 */
	public static final String NAME = "gettime";
	/**
	 * 平台时间,格式(yyyyMMddHHmmss)
	 */
	private long time;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		String str = jsonData.optString("time");
		if(str != null && str.trim().length() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date;
			try {
				date = sdf.parse(str);
			} catch (ParseException e) {
				throw new DataException("时间格式错误，yyyyMMddHHmmss");
			}
			this.time = date.getTime();
		}
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
