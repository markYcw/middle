package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 取消所有操作
 *
 * @author ycw
 * @see com.kedacom.middleware.epro.request.CancelAllRequest
 */
public class CancelAllResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
