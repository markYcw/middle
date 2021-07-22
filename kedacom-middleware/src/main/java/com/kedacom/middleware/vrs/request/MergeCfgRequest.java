package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.MergeCfgResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 刻录合成通道配置
 * @author ycw
 * @date 2021/5/26 19:21
 */
@Data
public class MergeCfgRequest extends VRSRequest{
    //是否刻录合成通道 0：不刻录 1：刻录
    private int isburnmerge;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("mergecfg");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("isburnmerge",isburnmerge);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new MergeCfgResponse();
    }
}
