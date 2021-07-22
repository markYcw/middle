package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * 15.刻录合成通道配置
 * @author ycw
 * @date 2021/5/26 19:25
 */
@Data
public class MergeCfgResponse extends VRSResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
