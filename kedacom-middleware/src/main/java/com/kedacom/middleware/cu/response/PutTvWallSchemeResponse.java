package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 2. 添加电视墙预案回复
 *
 * @author ycw
 * @date 2021/2/19
 */
public class PutTvWallSchemeResponse extends CuResponse {
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
