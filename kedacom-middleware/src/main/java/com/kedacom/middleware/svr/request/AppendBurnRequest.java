package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.AppendBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @seeAppendBurnResponse
 * @date 2021/4/21 10:01
 */
@Data
public class AppendBurnRequest extends SVRRequest {

    /**
     * 包名称
     */
    private String filepathname;

    /**
     * 光盘里的目录名
     */
    private String dirname;
    /**
     * 0:文件名全路径
     * 1:文件夹全路径
     */
    private int type;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("appendburn");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("type", type);
        data.put("filepathname", filepathname);
        data.put("dirname", dirname);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new AppendBurnResponse();
    }
}
