package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.mt.domain.MTType;
import com.kedacom.middleware.mt.request.GetMtTypeRequest;
import org.json.JSONObject;

/**
 * 
 * 终端响应（Response）：获取终端类型
 * @author TaoPeng
 * @see GetMtTypeRequest
 */
public class GetMtTypeResponse extends MTResponse {

	/**
	 * 终端类型
	 */
	private MTType type;
	
	@Override
	public void parseData(JSONObject jsonData) {
		super.parseResp(jsonData);
		
		int t = jsonData.optInt("type");
		this.type = MTType.parse(t);
		
	}

	public MTType getType() {
		return type;
	}

	public void setType(MTType type) {
		this.type = type;
	}
	
}
