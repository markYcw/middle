package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.StartDownloadrecRequest;
import org.json.JSONObject;

/**
 * SVR停止录像下载
 *
 * @author DengJie
 * @see StartDownloadrecRequest
 */
public class StopDownloadrecResponse extends SVRResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }


}
