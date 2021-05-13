package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.domain.DstAddr;
import com.kedacom.middleware.vrs.response.RecPlayResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 播放录像
 * @author ycw
 * @date 2021/5/12 17:28
 */
@Data
public class RecPlayRequest extends VRSRequest{
    //录像任务id
    private int rectaskid;
    //开始时间
    private int starttime;
    //结束时间
    private int endtime;
    //放像视频信息
    private List<DstAddr> dstAddrs;


    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("recplay");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("rectaskid", rectaskid);
        data.put("starttime",starttime);
        data.put("endtime",endtime);
        data.put("dstaddr",dstAddrs);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new RecPlayResponse();
    }
}
