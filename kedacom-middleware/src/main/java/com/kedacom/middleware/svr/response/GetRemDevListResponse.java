package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.SvrDevList;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/25 13:51
 * @description
 */
@Data
public class GetRemDevListResponse extends SVRResponse{

    private List<SvrDevList> devLists;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        devLists = new ArrayList<SvrDevList>();
        JSONArray devList = jsonData.optJSONArray("DevList");
        SvrDevList svrDevList = null;
        for (int i = 0; i < devList.length(); i++) {
            JSONObject jsonObject = devList.optJSONObject(i);
            svrDevList = new SvrDevList();
            svrDevList.setName(jsonObject.optString("name"));
            svrDevList.setUrl(jsonObject.optString("url"));
            devLists.add(svrDevList);
        }
    }
}
