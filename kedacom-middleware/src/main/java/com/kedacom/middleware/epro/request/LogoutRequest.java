package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登出E10Pro服务器
 *
 * @author ycw
 * @see LogoutResponse
 */
public class LogoutRequest extends EProRequest {

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("logout");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {

        return new LogoutResponse();
    }

}
