package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.StopRecRequest;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 停止录像响应
 *
 * @author ycw
 * @see StopRecRequest
 */
@Data
public class StopRecResponse extends ERroResponse{

    /**
     * 音频文件名
     */
    private String audio;

    /**
     * 视频频文件名
     */
    private String video;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.audio = jsonData.optString("audio");
        this.video = jsonData.optString("video");
    }
}
