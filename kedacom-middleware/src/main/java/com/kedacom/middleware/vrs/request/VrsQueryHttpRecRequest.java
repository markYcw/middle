package com.kedacom.middleware.vrs.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.vrs.response.VrsQueryHttpRecResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * http录像查询
 * @author ycw
 * @date 2021/6/21 13:43
 */
@Data
public class VrsQueryHttpRecRequest extends VRSRequest {

    /**
     * 每页的大小
     */
    private int pagesize;

    /**
     * 查询的页码（从1开始）
     */
    private int pagenum;

    /**
     * 模糊匹配的录像名字
     */
    private String includename;

    @Override
    public String toJson() throws JSONException {

        // Req部分
        JSONObject req = super.buildReq("vrsqueryhttprec");

        // data部分
        JSONObject data = new JSONObject();
        data.put("req", req);

        data.put("pagesize", pagesize);
        data.put("pagenum", pagenum);
        data.put("includename", includename);


        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public IResponse getResponse() {
        return new VrsQueryHttpRecResponse();
    }
}
