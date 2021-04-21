package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @author ycw
 * @Date 2021/4/21 10:27
 */
@Data
public class SupplementBurnResponse extends SVRResponse{

    /**
     *
     */
    private int burntaskid;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.burntaskid = jsonData.optInt("burntaskid");
    }
}
