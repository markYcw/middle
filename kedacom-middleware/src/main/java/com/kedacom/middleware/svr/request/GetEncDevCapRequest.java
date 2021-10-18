package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetEncDevCapResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/18 16:59
 * @description
 */
@Data
public class GetEncDevCapRequest extends SVRRequest{
    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getencdevcap");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetEncDevCapResponse();
    }
}
