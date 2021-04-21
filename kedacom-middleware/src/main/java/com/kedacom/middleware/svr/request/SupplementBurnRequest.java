package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SupplementBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @seeSupplementBurnResponse
 * @date 2021/4/21 10:01
 */
@Data
public class SupplementBurnRequest extends SVRRequest {

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 刻录模式 （选择DVD的模式）
     */
    private int mode;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("supplementburn");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("starttime", starttime);
        data.put("endtime", endtime);
        data.put("mode", mode);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new SupplementBurnResponse();
    }
}
