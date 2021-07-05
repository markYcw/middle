package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.CaptureRequest;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 抓拍图片响应
 *
 * @author ycw
 * @see CaptureRequest
 */
@Data
public class CaptureResponse extends ERroResponse{
    //抓拍到的图片路径
    private String capturePath;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.capturePath = jsonData.optString("capture_path");
    }
}
