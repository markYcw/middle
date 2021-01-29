package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.VcrStatus;
import com.kedacom.middleware.mcu.response.GetVcrStatusResponse;
import org.json.JSONObject;

/**
 * 录像机状态通知
 * @author TaoPeng
 *
 */
public class VcrStatusNotify extends McuNotify {


	public static final String NAME = "vcrstatnty";
	/**
	 * 录像机状态
	 */
	private VcrStatus vcrStatus;
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);

		JSONObject obj = jsonData.optJSONObject("vcrstat");
		VcrStatus vcrStatus = GetVcrStatusResponse.parseVcrStatus(obj);
		
		this.vcrStatus = vcrStatus;
	}


	public VcrStatus getVcrStatus() {
		return vcrStatus;
	}

	public void setVcrStatus(VcrStatus vcrStatus) {
		this.vcrStatus = vcrStatus;
	}


}
