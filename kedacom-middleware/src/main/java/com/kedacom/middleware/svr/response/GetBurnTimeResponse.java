package com.kedacom.middleware.svr.response;


import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取SVR时间
 * @seeGetBurnTimeRequest
 * @author ycw
 *
 */
@Data
public class GetBurnTimeResponse extends SVRResponse {

    /**
     * SVR时间,格式(yyyyMMddHHmmss)
     */
    private long time;
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        String str = jsonData.optString("systime");
        if(str != null && str.trim().length() > 0){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date;
            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                throw new DataException("时间格式错误，yyyyMMddHHmmss");
            }
            this.time = date.getTime();
        }
    }
}
