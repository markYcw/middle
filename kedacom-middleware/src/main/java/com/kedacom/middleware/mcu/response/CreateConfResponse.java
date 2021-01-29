package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.CreateConfRequest;
import org.json.JSONObject;

/**
 * 创建会议/模板
 * @see CreateConfRequest
 * @author YueZhipeng
 *
 */
public class CreateConfResponse extends McuResponse {

	/**
	 * 创会成功返回的唯一标识(5.0接口有效)
	 */
	private String confe164;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);

		this.confe164 = jsonData.optString("confe164");
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}
}
