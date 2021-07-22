package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.GetFingerPrintResponse;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * 手动获取指纹图片
 *
 * @author ycw
 * @see GetFingerPrintResponse
 */
public class GetFingerPrintRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("getfingerprint");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new GetFingerPrintResponse();
    }
}
