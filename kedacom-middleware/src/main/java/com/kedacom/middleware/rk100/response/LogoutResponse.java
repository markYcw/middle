package com.kedacom.middleware.rk100.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.gk.request.LogoutRequest;
import com.kedacom.middleware.gk.response.GKResponse;
import org.json.JSONObject;

/**
 * 登出RK100服务器
 *
 * @author LinChaoYu
 * @see LogoutRequest
 */
public class LogoutResponse extends RkResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
 