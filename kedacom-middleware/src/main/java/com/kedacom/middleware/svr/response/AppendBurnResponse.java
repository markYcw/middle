package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @author ycw
 * @Date 2021/4/21 10:01
 */
@Data
public class AppendBurnResponse extends SVRResponse{


    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
