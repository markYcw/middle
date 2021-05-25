package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * 录像室状态获取响应
 * @author ycw
 * @date 2021/5/24 10:08
 */
@Data
public class RecPlayVcrResponse extends VRSResponse {
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
