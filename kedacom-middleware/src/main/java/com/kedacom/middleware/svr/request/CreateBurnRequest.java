package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.CreateBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName CreateBurnRequest
 * @Description 新建刻录请求参数
 * @Author zlf
 * @Date 2021/5/31 14:07
 */
@Data
public class CreateBurnRequest extends SVRRequest {

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * 刻录模式 （选择DVD的模式）
     */
    private int mode;

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("createburn");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("starttime", starttime);
        data.put("endtime", endtime);
        data.put("mode", mode);

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
        return new CreateBurnResponse();
    }
}
