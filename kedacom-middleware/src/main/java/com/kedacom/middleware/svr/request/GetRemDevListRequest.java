package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetRemDevListResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/25 13:49
 * @description
 */
@Data
public class GetRemDevListRequest extends SVRRequest{

    private int start;

    private int count;

    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getremdevlist");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("start",start);
        data.put("count",count);
        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetRemDevListResponse();
    }
}
