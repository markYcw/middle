package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.DvdStatus;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * 系统状态获取响应
 * @author ycw
 * @date 2021/5/24 10:08
 */
@Data
public class GetSysStatusResponse extends VRSResponse{
    /**
     * 刻录工作模式
     * 0：实时刻录（录像并刻录）
     * 1：补刻
     * 2：只录像不刻录
     */
    private int burnworkmode;

    private List<DvdStatus> dvdStatuses;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
      super.parseResp(jsonData);
      this.burnworkmode = jsonData.optInt("burnworkmode");
        JSONArray array = jsonData.optJSONArray("dvdstatus");
        if(array != null || array.length() > 0){
            for(int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);
                String burntaskname = object.optString("burntaskname");
                int dvdstatus = object.optInt("dvdstatus");
                int totalcapacity = object.optInt("totalcapacity");
                int remaincapacity = object.optInt("remaincapacity");
                int burnprocess = object.optInt("burnprocess");
                int dvdworkstatus = object.optInt("dvdworkstatus");
                DvdStatus dvdStatus = new DvdStatus();
                dvdStatus.setBurntaskname(burntaskname);
                dvdStatus.setDvdstatus(dvdstatus);
                dvdStatus.setTotalcapacity(totalcapacity);
                dvdStatus.setRemaincapacity(remaincapacity);
                dvdStatus.setBurnprocess(burnprocess);
                dvdStatus.setDvdworkstatus(dvdworkstatus);
                dvdStatuses.add(dvdStatus);
            }
        }
    }


}
