package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：GetEncoderSiteRespose
 * 类描述：获取编码器的预置位
 * 创建人：lzs
 * 创建时间：2019-8-8 上午9:21:43
 */
public class GetEncoderSiteRespose extends SVRResponse {

    private int preset;//预置位

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.preset = jsonData.optInt("preset");
    }

    public int getPreset() {
        return preset;
    }

    public void setPreset(int preset) {
        this.preset = preset;
    }

}
