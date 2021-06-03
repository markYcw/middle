package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.SvrPtzCtrlResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName SvrPtzCtrlRequest
 * @Description PTZ控制请求参数
 * @Author zlf
 * @Date 2021/5/31 14:44
 */
@Data
public class SvrPtzCtrlRequest extends SVRRequest {

    /*
    控制命令Id
     */
    private int cmd;

    /*
    通道Id
     */
    private int chnid;

    /*
    参数1（1：向上移动；2：向下移动；3：向左移动；4：向右移动；5：回归；6：停止移动；7：拉近摄像头；8：拉远摄像头；14：摄象头预存；15：调摄象头预存；20：中心定位；21：局部放大；26：局部缩小）
     */
    private int param1;

    /**
     * 将对象转换成JSON字符串。
     *
     * @return
     * @throws JSONException
     */
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("ptzctrl");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("cmd", cmd);
        data.put("chnid", chnid);
        data.put("param1", param1);

        //返回
        String ret = data.toString();
        return ret;
    }

    /**
     * 获取此request对应的响应类(response)
     *
     * @return
     */
    @Override
    public IResponse getResponse() {
        return new SvrPtzCtrlResponse();
    }
}
