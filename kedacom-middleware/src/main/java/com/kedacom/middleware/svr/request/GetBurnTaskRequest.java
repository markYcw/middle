package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取刻录任务
 * @seeGetBurnTaskResponse
 * @author ycw
 * @date 2021/4/25 13:32
 */
@Data
public class GetBurnTaskRequest extends SVRRequest{

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getburntime");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("starttime", starttime);
        data.put("endtime", endtime);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return null;
    }
}
