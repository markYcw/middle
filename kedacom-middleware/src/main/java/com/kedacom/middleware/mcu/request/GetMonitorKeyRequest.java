package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.domain.IPAddr;
import com.kedacom.middleware.mcu.response.GetMonitorKeyResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求码流关键帧请求
 * @author ycw
 * @Date 2021/3/8 10:26
 */
@Data
public class GetMonitorKeyRequest extends McuRequest {

    /**
     * 会议e164号
     */
    private String conFe164;

    /**
     * 接受码流地址
     */
    private IPAddr dstaddr;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getmonitorkey");

        //dst部分
        JSONObject dstaddrJson = null;
        if(dstaddr != null){
            dstaddrJson = new JSONObject();
            dstaddrJson.put("ip", dstaddr.getIp());
            dstaddrJson.put("port", dstaddr.getPort());
        }

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("confe164", conFe164);
        data.putOpt("dstaddr", dstaddrJson);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetMonitorKeyResponse();
    }
}
