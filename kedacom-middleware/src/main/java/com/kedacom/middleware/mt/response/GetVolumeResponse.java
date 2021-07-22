package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * 音量获取响应
 * @author ycw
 * @Date 2021/4/8 13:51
 */
@Data
public class GetVolumeResponse extends MTResponse{
    /**
     * 音量大小，最大31
     */
    private int volume;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
        volume = jsonData.optInt("volume");
    }
}
