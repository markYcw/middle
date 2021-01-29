package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class LoadTvWallSchemeResponse extends CuResponse {
    public void parseData(JSONObject jsonData) throws DataException {
        parseResp(jsonData);
    }
}
