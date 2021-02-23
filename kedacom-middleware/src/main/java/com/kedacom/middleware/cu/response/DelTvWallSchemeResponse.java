package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 2. 删除电视墙预案回复
 * @author ycw
 * @Date 2021/2/23 11:04
 */
public class DelTvWallSchemeResponse extends CuResponse {
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
