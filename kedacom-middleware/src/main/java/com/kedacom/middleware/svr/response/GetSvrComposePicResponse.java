package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONObject;

/**
 * @ClassName GetSvrComposePicResponse
 * @Description 获取画面合成设置返回参数
 * @Author zlf
 * @Date 2021/5/31 14:30
 */
@Data
public class GetSvrComposePicResponse extends SVRResponse {
    /**
     * 解析数据。
     *
     * @param jsonData 符合JSON规范的字符串。
     * @throws DataException
     */
    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseResp(jsonData);
    }
}
