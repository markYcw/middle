package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.GetFingerPrintRequest;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 下发pdf文件响应
 *
 * @author ycw
 * @see GetFingerPrintRequest
 */
@Data
public class GetFingerPrintResponse extends ERroResponse{

    //指纹图片路径
    private String fingerprint_path;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.fingerprint_path = jsonData.optString("fingerprint_path");
    }
}
