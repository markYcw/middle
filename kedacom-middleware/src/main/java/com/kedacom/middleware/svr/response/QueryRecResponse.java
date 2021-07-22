package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 查询SVR录像
 * @seeQueryRecRequest
 * @author ycw
 * @date 2021/4/19 13:32
 */
@Data
public class QueryRecResponse extends SVRResponse{

    /**
     * 查询总数
     */
    private int num;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {

        super.parseResp(jsonData);
        this.num = jsonData.optInt("num");
    }
}
