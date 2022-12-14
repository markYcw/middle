package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.StopDownloadrecResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SVR停止录像下载
 *
 * @author DengJie
 * @see StopDownloadrecResponse
 */
@Data
public class StopDownloadrecRequest extends SVRRequest {
    /**
     * 下载句柄标示一个下载操作
     */
    private int downloadhandle;

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("stopdownloadrec");
        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("downloadhandle", downloadhandle);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {

        return new StopDownloadrecResponse();
    }

}
