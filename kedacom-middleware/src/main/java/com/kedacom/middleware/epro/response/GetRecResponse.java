package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.StartRecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 获取录像响应
 *
 * @author ycw
 * @see StartRecRequest
 */
public class GetRecResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
