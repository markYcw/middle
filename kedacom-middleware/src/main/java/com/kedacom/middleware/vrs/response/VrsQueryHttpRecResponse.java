package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.vrs.domain.RecInfo;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * http录像查询响应
 * @author ycw
 * @date 2021/6/21 15:08
 */
@Data
public class VrsQueryHttpRecResponse extends VRSResponse{
    /**
     * 录像总数
     */
    private int totlenum;

    /**
     * 录像集合
     */
    private List<RecInfo> resInfos = new ArrayList<RecInfo>();

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.totlenum = jsonData.optInt("totlenum");

        JSONArray jsonRecinfo = jsonData.optJSONArray("recinfo");
        if(jsonRecinfo != null){

            resInfos = new ArrayList<RecInfo>();

            for(int i = 0 ; i < jsonRecinfo.length(); i ++){
                try{
                    JSONObject obj = jsonRecinfo.getJSONObject(i);
                    String name = obj.optString("name");

                    String url = null;// http播放地址
                    if(!obj.isNull("url"))
                        url = obj.optString("url");

                    int duration = 0;// 持续时间（单位：秒）
                    if(!obj.isNull("duration"))
                        duration = obj.optInt("duration");

                    int starttime = 0;// 创建时间
                    if(!obj.isNull("starttime"))
                        starttime = obj.getInt("starttime");

                    RecInfo rec = new RecInfo();
                    rec.setName(name);
                    rec.setUrl(url);
                    rec.setStarttime(starttime);
                    rec.setDuration(duration);

                    resInfos.add(rec);
                }catch(JSONException e){
                    throw new DataException(e.getMessage(), e);
                }
            }
        }
    }
}
