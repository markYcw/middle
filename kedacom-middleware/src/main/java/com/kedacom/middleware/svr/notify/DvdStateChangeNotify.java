package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * @ClassName DvdStateChangeNotify
 * @Description dvd状态改变通知
 * @Author zlf
 * @Date 2021/6/18 9:52
 */
public class DvdStateChangeNotify extends SVRNotify {

    /**
     * 命令值
     */
    public static final String NAME = "dvdstatechange";

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
    }
}
