package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：VoiceActiveRespose
 * 类描述：
 * 创建人：lzs
 * 创建时间：2019-9-24 下午4:14:43
 */
public class VoiceActiveRespose extends SVRResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
