package com.kedacom.middleware.rk100.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.rk100.response.LogoutResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登出RK100服务器
 *
 * @author LinChaoYu
 * @see LogoutResponse
 */
public class LogoutRequest extends RkRequest {

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
