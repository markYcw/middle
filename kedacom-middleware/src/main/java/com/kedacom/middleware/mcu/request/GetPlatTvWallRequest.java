package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetPlatTvWallResponse;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 获取平台电视墙列表
 * @see GetPlatTvWallRequest
 * @author ycw
 * @Date 2021/3/24
 */
public class GetPlatTvWallRequest extends McuRequest {

    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getplattvwall");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetPlatTvWallResponse();
    }
}
