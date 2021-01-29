package com.kedacom.middleware.mt.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 终端结束点对点会议
 * @author LinChaoYu
 *
 */
public class StopP2PNotify extends MTNotify {

	public static final String NAME = "stopp2pnty";
	
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
