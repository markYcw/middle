package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.Chn;
import com.kedacom.middleware.svr.domain.RecInfo;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询SVR录像
 * @author ycw
 * @date 2021/4/19 13:44
 */
public class QueryRecNotify extends SVRNotify{

    /**
     * 命令值
     */
    public static final String NAME = "queryrecnty";

    /**
     * 录像集合
     */
    public static List<RecInfo> recInfos = new ArrayList<RecInfo>();

    /**
     * 是否传完
     */
    private boolean isend;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
        this.isend = jsonData.optBoolean("isend");
        JSONArray array = jsonData.optJSONArray("group");
        if (array != null) {
            this.parseData_recInfo(array);
        }
    }

    private void parseData_recInfo(JSONArray array) {
        for (int i = 0 ; i < array.length() ; i++){
            JSONObject jsonObj = array.optJSONObject(i);
            int id = jsonObj.optInt("id");
            int size = jsonObj.optInt("size");
            int resolution = jsonObj.optInt("resolution");
            JSONObject chn = jsonObj.optJSONObject("chn");
            Chn chnInside = new Chn();
            int devtype = chn.optInt("devtype");
            int devid = chn.optInt("devid");
            int chnid = chn.optInt("chnid");
            int taskid = chn.optInt("taskid");
            chnInside.setDevtype(devtype);
            chnInside.setDevid(devid);
            chnInside.setChnid(chnid);
            chnInside.setTaskid(taskid);
            String starttime = jsonObj.optString("starttime");
            String endtime = jsonObj.optString("endtime");
            String md5 = jsonObj.optString("md5");

            RecInfo recInfo = new RecInfo();
            recInfo.setId(id);
            recInfo.setSize(size);
            recInfo.setResolution(resolution);
            recInfo.setChn(chnInside);
            recInfo.setStarttime(starttime);
            recInfo.setEndtime(endtime);
            recInfo.setMd5(md5);
            recInfos.add(recInfo);
        }
    }

    public boolean isIsend() {
        return isend;
    }
}
