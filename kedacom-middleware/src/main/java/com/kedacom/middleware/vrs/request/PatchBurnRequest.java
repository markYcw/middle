package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.PatchBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 补刻
 * @author ycw
 * @date 2021/5/8 17:09
 */
@Data
public class PatchBurnRequest extends VRSRequest{
    /**
     * 刻录操作类型：1：补刻 2：中断补刻
     */
    private int opertype;

    /**
     * 录像任务ID
     */
    private int rectaskid;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("patchburn");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        data.put("opertype", opertype);
        data.put("rectaskid", rectaskid);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new PatchBurnResponse();
    }
}
