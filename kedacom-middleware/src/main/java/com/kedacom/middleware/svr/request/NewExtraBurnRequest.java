package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.NewExtraBurnResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/11/26 13:49
 * @description
 */
@Data
public class NewExtraBurnRequest extends SVRRequest{

    private String startTime;

    private String endTime;

    private int mode;

    private int taskId;

    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("newextraburn");
        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("starttime",startTime);
        data.put("endtime",endTime);
        data.put("mode",mode);
        data.put("taskid",taskId);
        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new NewExtraBurnResponse();
    }
}
