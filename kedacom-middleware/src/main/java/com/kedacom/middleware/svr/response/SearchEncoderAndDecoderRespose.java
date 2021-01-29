package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SearchEncoderAndDecoderRespose
 * 类描述：搜索编码器和解码器
 * 创建人：lzs
 * 创建时间：2019-8-20 上午9:13:59
 * @version
 *
 */
public class SearchEncoderAndDecoderRespose extends SVRResponse{

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
