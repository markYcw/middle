package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.BurnRecordResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询录像任务
 * @author ycw
 * @date 2021/5/18 14:56
 */
@Data
public class BurnRecordRequest extends VRSRequest{

    //笔录/附件目录名
    private String recorddirname;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("burnrecord");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("recorddirname", recorddirname);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new BurnRecordResponse();
    }
}
