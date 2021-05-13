package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * 申请录像室
 * @author ycw
 * @date 2021/5/8 16:35
 */
@Data
public class GetRoomResponse extends VRSResponse{

    private int roomid;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);

        this.roomid = jsonData.optInt("roomid");
    }
}
