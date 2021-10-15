package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.GetRemChnListResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/15 15:46
 * @description 获取远程点通道列表
 */
@Data
public class GetRemChnListRequest extends SVRRequest{


    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getremchnlist");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        //返回
        String ret = data.toString();
        return ret;

    }

    @Override
    public IResponse getResponse() {
        return new GetRemChnListResponse();
    }
}
