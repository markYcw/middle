package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 项目名称：kedacom-middleware
 * 类名称：GetDecoderNumRespose
 * 类描述：获取解码能力集
 * 创建人：lzs
 * 创建时间：2019-8-7 下午4:26:44
 */
public class GetDecoderNumRespose extends SVRResponse {

    private int num;//返回数量

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.num = jsonData.optInt("num");
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
