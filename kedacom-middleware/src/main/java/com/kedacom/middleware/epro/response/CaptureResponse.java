package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.CaptureRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 关闭设备相机
 *
 * @author ycw
 * @see CaptureRequest
 */
public class CaptureResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
