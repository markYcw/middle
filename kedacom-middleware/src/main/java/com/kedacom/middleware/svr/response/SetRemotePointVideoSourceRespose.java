package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：SetRemotePointVideoSourceRespose
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:48:02
 */
public class SetRemotePointVideoSourceRespose extends SVRResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
