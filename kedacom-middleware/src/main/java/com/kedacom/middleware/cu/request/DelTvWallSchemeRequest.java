package com.kedacom.middleware.cu.request;


import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.DelTvWallSchemeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 添加/修改电视墙预案
 * @author ycw
 * date 2021/2/19
 */
public class DelTvWallSchemeRequest extends CuRequest {

    //电视墙预案ID
    private String tvwallid;

    //电视墙预案名称
    private String name;
    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("deltvwallscheme");

        // data部分
        JSONObject data = new JSONObject();
        data.putOpt("req", req);
        data.putOpt("tvwallid", tvwallid);
        data.putOpt("name", name);

        // 返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public CuResponse getResponse() {
        return new DelTvWallSchemeResponse();
    }

    public String getTvwallid() {
        return tvwallid;
    }

    public void setTvwallid(String tvwallid) {
        this.tvwallid = tvwallid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
