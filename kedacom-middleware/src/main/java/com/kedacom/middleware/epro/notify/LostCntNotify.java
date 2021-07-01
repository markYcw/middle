package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * @ClassName E10Pro
 * @Description E10Pro服务器掉线通知
 * @Author ycw
 * @Date 2021/06/30 13:16
 */
public class LostCntNotify extends EProNotify {

    public static final String NAME = "lostcntnty";

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
    }
}
