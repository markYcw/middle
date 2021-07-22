package com.kedacom.middleware.epro.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.epro.response.PostPdfResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 下发pdf文件
 *
 * @author ycw
 * @see PostPdfResponse
 */
@Data
public class PostPdfRequest extends EProRequest{

    //pdf文件路径
    private String filepath;



    @Override
    public String toJson() throws JSONException {
        //Req部分
        JSONObject req = super.buildReq("postpdf");

        //data部分
        JSONObject data = new JSONObject();
        data.put("req", req);
        data.put("filepath", this.filepath);

        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new PostPdfResponse();
    }
}
