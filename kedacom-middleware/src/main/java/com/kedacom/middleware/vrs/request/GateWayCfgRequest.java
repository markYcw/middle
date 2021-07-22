package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.GateWayCfgResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 中间件配置
 * @author ycw
 * @date 2021/5/26 19:21
 */
@Data
public class GateWayCfgRequest extends VRSRequest{

    private String ip;

    private int port;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("gatewaycfg");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("ip",ip);
        data.put("port",port);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GateWayCfgResponse();
    }
}
