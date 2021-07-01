package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LogoutRequest;
import org.json.JSONObject;

/**
 * 登出E10Rro服务器
 *
 * @author ycw
 * @see LogoutRequest
 */
public class LogoutResponse extends ERroResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
 