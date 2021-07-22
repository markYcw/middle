package com.kedacom.middleware.epro.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LoginRequest;
import org.json.JSONObject;

/**
 * 登录E10Rro服务器
 *
 * @author ycw
 * @see LoginRequest
 */
public class LoginResponse extends ERroResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
