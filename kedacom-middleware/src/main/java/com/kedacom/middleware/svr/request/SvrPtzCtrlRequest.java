package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SvrPtzCtrlResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName SvrPtzCtrlRequest
 * @Description PTZ控制请求参数
 * @Author zlf
 * @Date 2021/5/31 14:44
 */
@Data
public class SvrPtzCtrlRequest extends SVRRequest {

    /*
    控制命令Id
     */
    private int cmd;

    /*
    通道Id
     */
    private int chnid;

    /*
    参数1,命令参数表
     */
    private int param1;

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("ptzctrl");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("cmd", cmd);
        data.put("chnid", chnid);
        data.put("param1", param1);

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
        return new SvrPtzCtrlResponse();
    }
}
