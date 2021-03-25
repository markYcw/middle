package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Hdu;
import com.kedacom.middleware.mcu.domain.HduId;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取电视墙列表
 * @see GetConfTvWallResponse
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class GetConfTvWallResponse extends McuResponse{

    private List<HduId> hduIds;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        ArrayList<HduId> list = new ArrayList<HduId>();
        if(!jsonData.isNull("hdus")){
            JSONArray hdus = jsonData.optJSONArray("hdus");
            if(hdus != null){
                for(int i = 0 ; i < hdus.length() ; i++){
                    try {
                        JSONObject obj = hdus.getJSONObject(i);
                        String hdu_id = obj.optString("hdu_id");
                        HduId hduId = new HduId();
                        hduId.setHdu_id(hdu_id);
                        list.add(hduId);
                    }catch (Exception e){
                        throw new DataException(e.getMessage(),e);
                    }
                }
            }
        }

        this.hduIds=list;
    }
}
