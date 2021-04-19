package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.QueryRecResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询SVR录像
 * @seeQueryRecResponse
 * @author ycw
 * @date 2021/4/19 13:32
 */
@Data
public class QueryRecRequest extends SVRRequest{
    /**
     * 查询录像的通道id，0表示合成通道
     */
    private int chnid;

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
        data.put("chnid", chnid);
        data.put("starttime", starttime);
        data.put("endtime", endtime);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new QueryRecResponse();
    }
}
