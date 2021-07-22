package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.RecLiveInfo;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 直播查询响应
 * @author ycw
 * @date 2021/6/21 15:08
 */
@Data
public class VrsQueryLiveResponse extends VRSResponse{
    /**
     * 录像总数
     */
    private int totlenum;

    /**
     * 录像集合
     */
    private List<RecLiveInfo> resInfos = new ArrayList<RecLiveInfo>();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.totlenum = jsonData.optInt("totlenum");

        JSONArray jsonRecinfo = jsonData.optJSONArray("recinfo");
        if(jsonRecinfo != null){

            resInfos = new ArrayList<RecLiveInfo>();

            for(int i = 0 ; i < jsonRecinfo.length(); i ++){
                try{
                    JSONObject obj = jsonRecinfo.getJSONObject(i);
                    String name = obj.optString("name");

                    String url = null;// http播放地址
                    if(!obj.isNull("url"))
                        url = obj.optString("url");


                    RecLiveInfo rec = new RecLiveInfo();
                    rec.setName(name);
                    rec.setUrl(url);

                    resInfos.add(rec);
                }catch(JSONException e){
                    throw new DataException(e.getMessage(), e);
                }
            }
        }
    }
}
