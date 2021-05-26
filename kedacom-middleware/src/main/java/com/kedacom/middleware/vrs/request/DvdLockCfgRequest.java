package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.DvdLockCfgResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * DVD封盘配置
 * @author ycw
 * @date 2021/5/26 19:21
 */
@Data
public class DvdLockCfgRequest extends VRSRequest{

    private int dvdautolock;


    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("dvdlockcfg");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("dvdautolock",dvdautolock);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new DvdLockCfgResponse();
    }
}
