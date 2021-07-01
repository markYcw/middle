package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.StopRecRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 停止录像响应
 *
 * @author ycw
 * @see StopRecRequest
 */
public class StopRecResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
