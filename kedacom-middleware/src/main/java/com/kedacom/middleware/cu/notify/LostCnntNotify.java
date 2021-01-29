package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 4. 平台断链通知
 * 
 * @author dengjie
 * 
 */
public class LostCnntNotify extends CuNotify {
	/**
	 * 命令值
	 */
	public static final String NAME = "lostcntnty";

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
	}

}
