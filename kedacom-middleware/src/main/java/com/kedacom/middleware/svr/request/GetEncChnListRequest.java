package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetEncChnListResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName GetEncChnListRequest
 * @Description 获取编码通道列表请求参数
 * @Author zlf
 * @Date 2021/5/31 15:08
 */
@Data
public class GetEncChnListRequest extends SVRRequest {

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getencchnlist");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetEncChnListResponse();
    }
}
