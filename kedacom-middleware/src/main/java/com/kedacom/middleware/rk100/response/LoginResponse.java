package com.kedacom.middleware.rk100.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LoginRequest;
import com.kedacom.middleware.gk.response.GKResponse;
import org.json.JSONObject;

/**
 * 登录RK100服务器
 *
 * @author LinChaoYu
 * @see LoginRequest
 */
public class LoginResponse extends RkResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {

        super.parseResp(jsonData);

    }

}
