package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.OpenCameraResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打开设备相机
 *
 * @author ycw
 * @see OpenCameraResponse
 */
public class OpenCameraRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("opencamera");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new OpenCameraResponse();
    }
}
