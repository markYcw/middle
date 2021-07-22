package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.OpenCameraRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 登出E10Rro服务器
 *
 * @author ycw
 * @see OpenCameraRequest
 */
public class OpenCameraResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
