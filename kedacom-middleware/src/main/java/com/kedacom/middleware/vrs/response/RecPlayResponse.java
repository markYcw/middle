package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 播放录像响应
 * @author ycw
 * @date 2021/5/8 16:35
 */
@Data
public class RecPlayResponse extends VRSResponse{
    //播放任务ID
    private int playtaskid;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.playtaskid=jsonData.optInt("playtaskid");
    }
}
