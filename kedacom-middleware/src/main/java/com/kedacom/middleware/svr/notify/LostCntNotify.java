package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * SVR掉线通知
 * @author TaoPeng
 *
 */
public class LostCntNotify extends SVRNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
