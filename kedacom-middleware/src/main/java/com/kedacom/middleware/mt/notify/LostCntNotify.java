package com.kedacom.middleware.mt.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 终端掉线通知
 * @author TaoPeng
 *
 */
public class LostCntNotify extends MTNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
