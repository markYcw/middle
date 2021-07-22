package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 录像室控制响应
 * @author ycw
 * @date 2021/5/8 17:09
 */
public class RoomCtrlResponse extends VRSResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
