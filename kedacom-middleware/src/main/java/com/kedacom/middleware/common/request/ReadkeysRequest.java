package com.kedacom.middleware.common.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.common.CommonRequest;
import com.kedacom.middleware.common.response.ReadkeysResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @date 2021/5/26
 */
@Data
public class ReadkeysRequest extends CommonRequest {

    /**
     * 文件key路径。仅文件key有效。默认值c:\\kedacom\\licenses\\kedalicense.key
     * 或/usr/bin/kedalicense.key
     */
    private String filekey;

    @Override
    public String toJson() throws JSONException {
        JSONObject req = super.buildReq("readkeys");

        JSONObject data = new JSONObject();
        data.put("req", req);
        data.putOpt("filekey", filekey);

        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new ReadkeysResponse();
    }
}
