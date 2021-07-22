package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.RecPlayResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 播放录像控制
 * @author ycw
 * @date 2021/5/24 10:06
 */
@Data
public class RecPlayVcrRequest extends VRSRequest{

    private int playtaskid;

    private int vcrcmd;

    private String dragtimes;

    private int jumptime;

    private int playrate;


    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("recplayvcr");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("playtaskid", playtaskid);
        data.put("vcrcmd",vcrcmd);
        data.put("dragtimes",dragtimes);
        data.put("jumptime",jumptime);
        data.put("playrate",playrate);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new RecPlayResponse();
    }
}
