package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @author ycw
 * @Date 2021/11/26 10:01
 */
@Data
public class NewExtraBurnResponse extends SVRResponse{


    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
