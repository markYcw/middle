package com.kedacom.middleware.svr.response;


import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取SVR时间
 *
 * @author ycw
 * @seeGetBurnTimeRequest
 */
@Data
public class GetBurnTimeResponse extends SVRResponse {

    /**
     * SVR时间,格式(yyyyMMddHHmmss)
     */
    private String time;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        String str = jsonData.optString("systime");
        this.time = str;
    }
}
