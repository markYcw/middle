package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.KillBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName KillBurnRequest
 * @Description 废止刻录请求参数
 * @Author zlf
 * @Date 2021/5/31 14:17
 */
@Data
public class KillBurnRequest extends SVRRequest {
    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("killburn");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    /**
     * 获取此request对应的响应类(response)
     *
     * @return
     */
    @Override
    public IResponse getResponse() {
        return new KillBurnResponse();
    }
}
