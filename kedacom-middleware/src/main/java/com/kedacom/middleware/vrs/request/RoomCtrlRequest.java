package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.RoomCtrlResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 录像室控制
 * @author ycw
 * @date 2021/5/8 17:31
 */
@Data
public class RoomCtrlRequest extends VRSRequest {
    /**
     * 控制类型
     * 1：开始录像
     * 2：暂停录像
     * 3：恢复录像
     * 4：停止录像
     * 5：开启双流
     * 6：停止双流
     */
    private int ctrltype;

    /**
     * 录像室Id
     */
    private int roomid;

    /**
     * 是否本地
     * 0：本地
     * 1：远端
     * 备注: 当 ctrltype 为5或6有效
     */
    private int islocal;
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("roomctrl");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        data.put("ctrltype", ctrltype);
        data.put("roomid", roomid);
        data.put("islocal", islocal);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new RoomCtrlResponse();
    }
}
