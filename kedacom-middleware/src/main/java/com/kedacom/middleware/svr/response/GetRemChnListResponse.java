package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.SvrChnList;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/15 15:49
 * @description
 */
@Data
public class GetRemChnListResponse extends SVRResponse{

    /*
  远程点通道列表
   */
    private List<SvrChnList> ChnList;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        ChnList = new ArrayList<SvrChnList>();
        JSONArray encChnListArray = jsonData.optJSONArray("ChnList");
        for (int i = 0; i < encChnListArray.length(); i++) {
            JSONObject jsonObject = encChnListArray.optJSONObject(i);
            SvrChnList svrChnList = new SvrChnList();
            svrChnList.setChnId(jsonObject.optInt("ChnId"));
            svrChnList.setChnAlias(jsonObject.optString("ChnAlias"));
            svrChnList.setUrl(jsonObject.optString("url"));
            svrChnList.setIsOnline(jsonObject.optInt("IsOnline"));
            ChnList.add(svrChnList);
        }
    }
}
