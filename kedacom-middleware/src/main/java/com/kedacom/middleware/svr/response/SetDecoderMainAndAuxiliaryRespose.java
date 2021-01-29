package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：SetDecoderMainAndauxiliaryRespose
 * 类描述：设置解码器的解码通道和主辅流
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:52:37
 * @version
 *
 */
public class SetDecoderMainAndAuxiliaryRespose extends SVRResponse{
	
	private int errorcode;//返回值

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
