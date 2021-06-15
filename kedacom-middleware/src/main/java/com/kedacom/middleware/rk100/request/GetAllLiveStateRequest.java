package com.kedacom.middleware.rk100.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.rk100.response.GetAllLiveStateResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName GetAllLiveStateRequest
 * @Description 获取所有开关状态请求参数类
 * @Author zlf
 * @Date 2021/6/10 16:22
 */
@Data
public class GetAllLiveStateRequest extends RkRequest {

    /*
    设备唯一标识
     */
    private String deviceSn;

    /*
    写死 allLiveState
     */
    private final String messageType = "allLiveState";

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getalllivestate");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("device_sn", this.getDeviceSn());
        data.put("message_type", this.getMessageType());

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
        return new GetAllLiveStateResponse();
    }
}
