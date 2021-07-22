package com.kedacom.middleware.rk100.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * @Description RK100掉线通知
 * @param:
 * @return:
 * @author:zlf
 * @date:
 */
public class LostCntNotify extends RKNotify {

    public static final String NAME = "lostcntnty";

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
    }
}
