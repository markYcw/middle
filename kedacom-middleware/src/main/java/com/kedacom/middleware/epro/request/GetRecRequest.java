package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.GetRecResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取录像
 *
 * @author ycw
 * @see GetRecResponse
 */
@Data
public class GetRecRequest extends EProRequest{

    /**
     * 要获取的文件名
     */
    private String file;

    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getrec");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("file", file);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetRecResponse();
    }
}
