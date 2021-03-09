package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**请求浏览码流关键帧响应
 * @author ycw
 * @Date 2021/3/8 16:14
 */
@Data
public class GetMonitorKeyResponse extends McuResponse {
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
