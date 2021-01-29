package com.kedacom.middleware.vrs.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 录播服务器 掉线通知
 * @author LinChaoYu
 *
 */
public class LostCntNotify extends VRSNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
