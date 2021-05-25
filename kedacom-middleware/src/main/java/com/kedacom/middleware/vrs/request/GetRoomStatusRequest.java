package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.GetRoomStatusResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 录像室状态获取
 * @author ycw
 * @date 2021/5/24 10:06
 */
@Data
public class GetRoomStatusRequest extends VRSRequest{


    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getroomstatus");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetRoomStatusResponse();
    }
}
