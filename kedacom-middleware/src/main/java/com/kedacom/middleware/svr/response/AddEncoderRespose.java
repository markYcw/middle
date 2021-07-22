package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：AddEncoderRespose
 * 类描述：添加编码器返回
 * 创建人：lzs
 * 创建时间：2019-7-19 下午5:13:51
 */
public class AddEncoderRespose extends SVRResponse {

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }

}
