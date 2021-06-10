package com.kedacom.middleware.rk100.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.rk100.response.ListRkResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName ListRkRequest
 * @Description 获取RK100信息请求参数类
 * @Author zlf
 * @Date 2021/6/10 14:56
 */
@Data
public class ListRkRequest extends RkRequest {

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {

        //Req部分
        JSONObject req = super.buildReq("listrk");

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
        return new ListRkResponse();
    }
}
