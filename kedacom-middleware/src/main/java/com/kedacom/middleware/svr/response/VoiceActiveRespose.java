package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：VoiceActiveRespose
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-9-24 下午4:14:43
 * @version
 *
 */
public class VoiceActiveRespose extends SVRResponse{

	private int errorcode;//返回的结果

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.errorcode = jsonData.optInt("errorcode");
	}

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

}
