package com.kedacom.middleware.mcu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 平台断链通知
 * @author TaoPeng
 *
 */
public class LostCntNotify extends McuNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
