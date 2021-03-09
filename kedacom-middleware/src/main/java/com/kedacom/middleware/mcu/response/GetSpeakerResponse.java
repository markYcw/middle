package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 获取发言人响应结果
 * @author ycw
 * @Date 2021/3/9 10:14
 */
@Data
public class GetSpeakerResponse extends McuResponse {

    private String mtInfo;
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        mtInfo = jsonData.optString("mtinfo");
    }
}
