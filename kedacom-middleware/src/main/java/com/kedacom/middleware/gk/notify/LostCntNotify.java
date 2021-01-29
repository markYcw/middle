package com.kedacom.middleware.gk.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * GK掉线通知
 * @author LinChaoYu
 *
 */
public class LostCntNotify extends GKNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
