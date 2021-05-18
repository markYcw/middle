package com.kedacom.middleware.vrs.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;
/**
 * 刻录附件响应
 * @author ycw
 * @date 2021/5/18 15:00
 */
@Data
public class BurnRecordResponse extends VRSResponse{
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
