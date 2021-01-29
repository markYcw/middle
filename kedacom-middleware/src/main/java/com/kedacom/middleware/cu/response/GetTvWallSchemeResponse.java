package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

public class GetTvWallSchemeResponse extends CuResponse {
    private int num;

    public void parseData(JSONObject jsonData) throws DataException {
        parseResp(jsonData);
        this.num = jsonData.optInt("num");
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
