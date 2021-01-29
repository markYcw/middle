package com.kedacom.middleware.mt.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 终端被抢占通知
 * @author TaoPeng
 *
 */
public class MTRobbedNotify extends MTNotify {


	public static final String NAME = "mtrobbednty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
