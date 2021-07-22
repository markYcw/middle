package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * 获取刻录任务
 *
 * @author ycw
 * @seeGetBurnTaskRequest
 * @date 2021/4/25 13:32
 */
@Data
public class GetBurnTaskResponse extends SVRResponse {
    /**
     * 查询总数
     */
    private int num;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.num = jsonData.optInt("sum");
    }
}
