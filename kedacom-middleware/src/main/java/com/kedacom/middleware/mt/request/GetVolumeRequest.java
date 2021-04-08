package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetVolumeResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 音量获取请求
 * @author ycw
 * @Date 2021/4/8 13:51
 */
@Data
public class GetVolumeRequest extends MTRequest{

    private int type;

    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getvolume");

        //Data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("type", type);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetVolumeResponse();
    }
}
