package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.GetSysStatusResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 系统状态获取
 * @author ycw
 * @date 2021/5/24 15:48
 */
@Data
public class GetSysStatusRequest extends VRSRequest{
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getsysstatus");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetSysStatusResponse();
    }
}
