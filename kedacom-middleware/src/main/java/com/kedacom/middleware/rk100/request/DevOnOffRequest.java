package com.kedacom.middleware.rk100.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.rk100.response.DevOnOffResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName DevOnOffRequest
 * @Description 发起开关请求参数类
 * @Author zlf
 * @Date 2021/6/10 16:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DevOnOffRequest extends RkRequest {

    /*
    设备唯一编号
     */
    private String deviceSn;

    /*
    该信令写死rpc
     */
    private final String messageType = "rpc";

    /*
    on 开机； off关机
     */
    private String data;

    /*
    获取信息时得到的
     */
    private String componentId;

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("devonoff");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("data", this.getData());
        data.put("component_id", this.getComponentId());
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
        return new DevOnOffResponse();
    }
}
