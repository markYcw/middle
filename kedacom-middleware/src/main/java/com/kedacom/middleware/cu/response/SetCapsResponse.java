package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 3. 设置能力集
 * 
 * @author dengjie
 * 
 */
public class SetCapsResponse extends CuResponse {

	/**
	 * 平台是否支持端口协商（只有客户端和平台都支持端口协商，在语音呼叫时才能用端口协商模式）
	 */
	private int supportconsult;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.supportconsult = jsonData.optInt("supportconsult");
	}

	public int getSupportconsult() {
		return supportconsult;
	}

	public void setSupportconsult(int supportconsult) {
		this.supportconsult = supportconsult;
	}

}
