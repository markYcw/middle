package com.kedacom.middleware.upu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * UPU掉线通知
 * 
 * @author LinChaoYu
 *
 */
public class LostCntNotify extends UPUNotify {


	public static final String NAME = "lostcntnty";
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		
		super.parseNty(jsonData);
		
	}
}
