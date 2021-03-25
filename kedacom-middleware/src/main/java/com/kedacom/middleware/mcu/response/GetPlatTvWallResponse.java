package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mcu.domain.Hdu;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取电视墙列表
 * @see GetPlatTvWallResponse
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class GetPlatTvWallResponse extends McuResponse {

    private List<Hdu> hdus;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        ArrayList<Hdu> list = new ArrayList<Hdu>();
        if(!jsonData.isNull("hdus")){
            JSONArray hdus = jsonData.optJSONArray("hdus");
            if(hdus != null){
                for(int i = 0 ; i < hdus.length() ; i++){
                    try {
                        JSONObject obj = hdus.getJSONObject(i);
                        String hdu_id = obj.optString("hdu_id");
                        String hdu_name = obj.optString("hdu_name");
                        int occupy = obj.optInt("occupy");
                        int online = obj.optInt("online");
                        Hdu hdu = new Hdu();
                        hdu.setHdu_id(hdu_id);
                        hdu.setHdu_name(hdu_name);
                        hdu.setOccupy(occupy);
                        hdu.setOnline(online);
                        list.add(hdu);
                    }catch (Exception e){
                        throw new DataException(e.getMessage(),e);
                    }
                }
            }
        }

        this.hdus=list;
    }
}
