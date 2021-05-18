package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.RecTaskInfo;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 补刻响应
 * @author ycw
 * @date 2021/5/8 17:09
 */
@Data
public class QueryRecTaskResponse extends VRSResponse{
    //0：所有返回数据均有效
    //1：仅totalnum有效
    //2：totalnum无效，后来的数据有效
    private int resulttype;

    //当前查询数目
    private int curnum;

    //查询的总条数
    private int totalnum;

    //录像信息
    private List<RecTaskInfo> recTaskInfos;


    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.resulttype = jsonData.optInt("resulttype");
        this.curnum = jsonData.optInt("curnum");
        this.totalnum = jsonData.optInt("totalnum");

        JSONArray array = jsonData.optJSONArray("rectaskinfo");
        if(array != null && array.length() > 0){
            recTaskInfos = new ArrayList<RecTaskInfo>();
            for (int i = 0; i< array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                RecTaskInfo info = new RecTaskInfo();
                int taskid = object.optInt("taskid");
                info.setTaskid(taskid);
                String taskname = object.optString("taskname");
                info.setTaskname(taskname);
                String starttime = object.optString("starttime");
                info.setStarttime(starttime);
                String endtime = object.optString("endtime");
                info.setEndtime(endtime);
                int size = object.optInt("size");
                info.setSize(size);
                int ispublish = object.optInt("ispublish");
                info.setIspublish(ispublish);
                String remark = object.optString("remark");
                info.setRemark(remark);
                recTaskInfos.add(info);
            }
        }
    }
}
