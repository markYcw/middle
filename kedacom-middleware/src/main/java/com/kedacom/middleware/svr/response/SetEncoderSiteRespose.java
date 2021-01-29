package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SetEncoderSiteRespose
 * 类描述：设置编码器的预置位
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:22:00
 * @version
 *
 */
public class SetEncoderSiteRespose extends SVRResponse{
	
	private int errorcode;

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
