package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.QueryRecTaskResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 查询录像任务
 * @author ycw
 * @date 2021/5/18 10:36
 */
@Data
public class QueryRecTaskRequest extends VRSRequest{
    //查询类型
    //0:获取总条目数以及按照索引和返回数目获取对应的条目
    //1：获取总条目数
    //2：按照索引和返回数目获取对应的条目
    private int querytype;

    //查询条件 0：无效
    // 0x0001 : by 时间
    // 0x0002 : by 关键字
    // 0x0004 :by 组名
    private int querymask;
    //开始时间
    private String starttimes;

    //结束时间
    private String endtimes;

    //关键字内容
    private String content;

    //最大查询数目
    private int maxnum;

    //查询起始下标
    private int startindex;

    //排序 0：降序 1：升序
    private int sort;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("queryrectask");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("querytype", querytype);
        data.put("querymask",querymask);
        data.put("starttimes",starttimes);
        data.put("endtimes",endtimes);
        data.put("content",content);
        data.put("maxnum",maxnum);
        data.put("startindex",startindex);
        data.put("sort",sort);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new QueryRecTaskResponse();
    }
}
