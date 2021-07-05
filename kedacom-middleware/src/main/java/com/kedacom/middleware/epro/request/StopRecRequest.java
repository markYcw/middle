package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.StopRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止录像
 *
 * @author ycw
 * @see StopRecResponse
 */
public class StopRecRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("stoprec");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new StopRecResponse();
    }
}
