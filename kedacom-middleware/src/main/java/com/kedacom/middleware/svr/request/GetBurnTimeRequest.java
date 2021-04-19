package com.kedacom.middleware.svr.request;


import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetBurnTimeResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取SVR时间
 * @seeGetBurnTimeResponse
 * @author ycw
 *
 */
@Data
public class GetBurnTimeRequest extends SVRRequest{
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getburntime");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetBurnTimeResponse();
    }
}
