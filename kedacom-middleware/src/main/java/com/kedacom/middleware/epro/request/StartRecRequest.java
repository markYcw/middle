package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.StartRecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 启动录像
 *
 * @author ycw
 * @see StartRecResponse
 */
public class StartRecRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("startrec");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new StartRecResponse();
    }
}
