package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 获取主席响应结果
 * @author ycw
 * @Date 2021/3/9 11:14
 */
@Data
public class GetChairManResponse extends McuResponse {

    private String mtInfo;
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        mtInfo = jsonData.optString("mtinfo");
    }
}
