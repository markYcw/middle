package com.kedacom.middleware.rk100.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @ClassName DevOnOffResponse
 * @Description TODO
 * @Author zlf
 * @Date 2021/6/10 16:17
 */
@Data
public class DevOnOffResponse extends RkResponse {

    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     * @throws DataException
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
