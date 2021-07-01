package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.StopRecResponse;
import org.json.JSONException;
/**
 * 停止录像
 *
 * @author ycw
 * @see StopRecResponse
 */
public class StopRecRequest extends EProRequest{
    @Override
    public String toJson() throws JSONException {
        return null;
    }

    @Override
    public IResponse getResponse() {
        return new StopRecResponse();
    }
}
