package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * DVD封盘配置
 * @author ycw
 * @date 2021/5/26 19:25
 */
@Data
public class DvdLockCfgResponse extends VRSResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
