package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetSpeakerResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取发言人请求
 * @author ycw
 * @Date 2021/3/9 10:34
 */
@Data
public class GetSpeakerRequest extends McuRequest {
    /**
     * 会议e164号
     */
    private String conFe164;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getspeaker");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("confe164", conFe164);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetSpeakerResponse();
    }
}
