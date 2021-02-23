package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * @author ycw
 * @Date 2021/2/23 13:19:52
 */
public class LoadTvWallSchemeResponse extends CuResponse {
    public void parseData(JSONObject jsonData) throws DataException {
        parseResp(jsonData);
    }
}
