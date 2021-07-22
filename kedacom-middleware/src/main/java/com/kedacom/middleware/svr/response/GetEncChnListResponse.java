package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.domain.SvrEncChnList;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName GetEncChnListRequest
 * @Description 获取编码通道列表返回参数
 * @Author zlf
 * @Date 2021/5/31 15:09
 */
@Data
public class GetEncChnListResponse extends SVRResponse {

    /*
    编码通道列表
     */
    private List<SvrEncChnList> encChnList;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        encChnList = new ArrayList<SvrEncChnList>();
        JSONArray encChnListArray = jsonData.optJSONArray("EncChnList");
        SvrEncChnList svrEncChnList = null;
        for (int i = 0; i < encChnListArray.length(); i++) {
            JSONObject jsonObject = encChnListArray.optJSONObject(i);
            svrEncChnList = new SvrEncChnList();
            svrEncChnList.setChnId(jsonObject.optInt("ChnId"));
            svrEncChnList.setDevId(jsonObject.optInt("DevId"));
            svrEncChnList.setDevType(jsonObject.optInt("DevType"));
            svrEncChnList.setIsValid(jsonObject.optInt("IsValid"));
            svrEncChnList.setIsOnline(jsonObject.optInt("IsOnline"));
            svrEncChnList.setIsRec(jsonObject.optInt("IsRec"));
            svrEncChnList.setIsSupSecStream(jsonObject.optInt("IsSupSecStream"));
            svrEncChnList.setIsStartUpSdi(jsonObject.optInt("IsStartUpSdi"));
            svrEncChnList.setChnAlias(jsonObject.optString("ChnAlias"));
            encChnList.add(svrEncChnList);
        }
    }
}
