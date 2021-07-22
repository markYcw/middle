package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.domain.MtInfo;
import com.kedacom.middleware.vrs.domain.StreamTcpPort;
import com.kedacom.middleware.vrs.response.GetRoomResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 申请录像室
 * @author ycw
 * @date 2021/5/8 13:35
 */
@Data
public class GetRoomRequest extends VRSRequest{
    /**
     * 录像任务名称
     */
    private String rectaskname;

    /**
     * 是否刻录
     */
    private int isburn;

    /**
     * 0：单点会议 1：点对点会议
     * 说明：
     * 单点会议只录本地流
     * 点对点会议时登录两个终端取本地流
     * 点对点会议时不在会议中会主动组会
     */
    private int conferencetype;

    /**
     * 码流方式
     * 0：中间件申请
     * 1：SDK申请
     * 2：被动接收
     */
    private int streammode;

    /**
     * 终端信息
     */
    private List<MtInfo> mtinfo;

    /**
     *
     */
    private List<StreamTcpPort> streamrtcpport;

    /**
     * 是否控制终端组会，启用双流等 0：否  1：是
     */
    private int ctrlmt;


    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("getroom");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        data.put("rectaskname", rectaskname);
        data.put("isburn", isburn);
        data.put("conferencetype", conferencetype);
        data.put("streammode",streammode);
        data.put("mtinfo",mtinfo);
        data.put("streamrtcpport",streamrtcpport);
        data.put("ctrlmt",ctrlmt);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetRoomResponse();
    }
}
