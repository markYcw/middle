package com.kedacom.middleware.mcu.request;


import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetConfTvWallResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取会议电视墙列表
 * @see GetConfTvWallRequest
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class GetConfTvWallRequest extends McuRequest{

    private String confe164;

    @Override
    public String toJson() throws JSONException {
        JSONObject req = super.buildReq("getconftvwall");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("confe164", confe164);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetConfTvWallResponse();
    }
}
