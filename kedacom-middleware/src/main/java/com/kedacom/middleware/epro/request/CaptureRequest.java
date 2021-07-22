package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.CaptureResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 抓拍图片
 *
 * @author ycw
 * @see CaptureResponse
 */
public class CaptureRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("capture");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new CaptureResponse();
    }
}
