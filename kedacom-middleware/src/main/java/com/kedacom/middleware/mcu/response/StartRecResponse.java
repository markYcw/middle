package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.request.StartRecRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开始录像
 * @see StartRecRequest
 * @author YueZhipeng
 * 
 * 2018-02-02 LinChaoYu 适配会议平台5.0及以上版本
 *
 */
public class StartRecResponse extends McuResponse {
	
	/**
	 * 录像ID（会议平台5.0及以上版本）
	 */
	private String recid;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        
        try {
        	
        	if(!jsonData.isNull("recid"))
        		this.setRecid(jsonData.getString("recid"));//返回的录像ID
        	
		} catch (JSONException e) {
			throw new DataException(e.getMessage(), e);
		}
	}

	public String getRecid() {
		return recid;
	}

	public void setRecid(String recid) {
		this.recid = recid;
	}
}
