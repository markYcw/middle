package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetTvWallChnInfoResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取会议电视墙通道信息
 * @see GetTvWallChnInfoRequest
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class GetTvWallChnInfoRequest extends McuRequest{
    /**
     * 会议ID
     */
    private String confe164;

    /**
     * 电视墙ID
     */
    private int eqpid;


    @Override
    public String toJson() throws JSONException {

        JSONObject req = super.buildReq("gettvwallchninfo");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("confe164", confe164);
        data.put("eqpid",eqpid);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetTvWallChnInfoResponse();
    }
}
