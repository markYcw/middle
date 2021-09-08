package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.QueryRecFileResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 10:54
 * @description
 */
@Data
public class QueryRecFileRequest extends VRSRequest{

    //查询类型
    //0:获取总条目数以及按照索引和返回数目获取对应的条目
    //1：获取总条目数
    //2：按照索引和返回数目获取对应的条目
    private int querytype;

    //查询条件
    //0：无效
    //0x0001：录像任务id
    private int querymask;

    //录像任务ID
    private int rectaskid;

    //最大查询数目
    private int maxnum;

    //查询起始下标
    private int startindex;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("queryrecfile");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        data.put("querytype", querytype);
        data.put("querymask", querymask);
        data.put("rectaskid", rectaskid);
        data.put("maxnum", maxnum);
        data.put("startindex", startindex);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new QueryRecFileResponse();
    }
}
