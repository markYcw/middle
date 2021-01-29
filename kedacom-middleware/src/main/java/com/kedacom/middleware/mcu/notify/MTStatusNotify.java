package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.MTStatus;
import com.kedacom.middleware.mcu.response.GetMTStatusResponse;
import org.json.JSONObject;

/**
 * 终端状态通知
 * @author TaoPeng
 *
 */
public class MTStatusNotify extends McuNotify {

	public static final String NAME = "mtstatusnty";
	
	/**
	 * 终端状态
	 */
	private MTStatus mtStatus;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		JSONObject obj = jsonData.optJSONObject("mtstatus");
		if(obj != null){
			this.mtStatus = GetMTStatusResponse.parseMTStatus(obj);
		}
	}

	public MTStatus getMtStatus() {
		return mtStatus;
	}

	public void setMtStatus(MTStatus mtStatus) {
		this.mtStatus = mtStatus;
	}
}
