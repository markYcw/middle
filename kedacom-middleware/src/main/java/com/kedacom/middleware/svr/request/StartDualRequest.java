package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.StartDualResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName StartDualRequest
 * @Description 发送双流请求参数
 * @Author zlf
 * @Date 2021/5/31 14:50
 */
@Data
public class StartDualRequest extends SVRRequest {

    /*
    远程点通道号
     */
    private int chnid;

    /*
    true表示发送双流，false表示不发送或停止发送
     */
    private boolean dual;

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("startdual");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("chnid", chnid);
        data.put("dual", dual);

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
        return new StartDualResponse();
    }
}
