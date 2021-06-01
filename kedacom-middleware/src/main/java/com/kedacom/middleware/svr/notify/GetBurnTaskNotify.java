package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.BurnTaskInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * 获取刻录任务
 * @author ycw
 * @date 2021/4/19 13:44
 */
public class GetBurnTaskNotify extends SVRNotify {

    /**
     * 命令值
     */
    public static final String NAME = "getburntasknty";

    /**
     * 录像集合
     */
    public static List<BurnTaskInfo> burnTaskInfos = new ArrayList<BurnTaskInfo>();

    /**
     * 是否传完
     */
    private boolean isend;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        this.isend = jsonData.optBoolean("isend");
        JSONArray array = jsonData.optJSONArray("burntaskinfo");
        if (array != null) {
            this.parseData_burnTaskInfo(array);
        }
    }

    private void parseData_burnTaskInfo(JSONArray array) {
        for (int i = 0 ; i < array.length() ; i++){
            JSONObject jsonObj = array.optJSONObject(i);
            int burntaskid = jsonObj.optInt("burntaskid");
            int burntaskdbid = jsonObj.optInt("burntaskdbid");
            String starttime = jsonObj.optString("starttime");
            String durationtime = jsonObj.optString("durationtime");
            BurnTaskInfo burnTaskInfo = new BurnTaskInfo();
            burnTaskInfo.setBurntaskid(burntaskid);
            burnTaskInfo.setBurntaskdbid(burntaskdbid);
            burnTaskInfo.setStarttime(starttime);
            burnTaskInfo.setDurationtime(durationtime);
            burnTaskInfos.add(burnTaskInfo);
        }
        
    }

}
