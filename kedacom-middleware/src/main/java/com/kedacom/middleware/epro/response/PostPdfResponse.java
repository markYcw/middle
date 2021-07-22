package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.epro.request.PostPdfRequest;
import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;
/**
 * 下发pdf文件响应
 *
 * @author ycw
 * @see PostPdfRequest
 */
public class PostPdfResponse extends ERroResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
