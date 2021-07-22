package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.StartDownloadrecRequest;
import lombok.Data;
import org.json.JSONObject;

/**
 * SVR录像下载
 *
 * @author DengJie
 * @see StartDownloadrecRequest
 */
@Data
public class StartDownloadrecResponse extends SVRResponse {

    /**
     *
     */
    private Integer downloadhandle;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.downloadhandle = jsonData.optInt("downloadhandle");
    }

    public Integer getDownloadhandle() {
        return downloadhandle;
    }

    public void setDownloadhandle(Integer downloadhandle) {
        this.downloadhandle = downloadhandle;
    }

}
