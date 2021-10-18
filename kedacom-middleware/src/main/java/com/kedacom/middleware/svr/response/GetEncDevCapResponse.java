package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/18 17:00
 * @description
 */
@Data
public class GetEncDevCapResponse extends SVRResponse{
    /**
     * 编码器通道数
     */
    private int num;

    /**
     *远程通道起始id
     */
    private int RemChnIdStart;

    /**
     *远程通道总数
     */
    private int RemChnCount;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        this.num = jsonData.optInt("num");
        this.RemChnIdStart = jsonData.optInt("RemChnIdStart");
        this.RemChnCount = jsonData.optInt("RemChnCount");
    }
}
