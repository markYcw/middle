package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetTvWallSchemeResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class GetTvWallSchemeRequest extends CuRequest {
    private String tvwallid;

    public String toJson() throws JSONException {
        JSONObject req = buildReq("gettvwallscheme");
        JSONObject data = new JSONObject();
        data.putOpt("req", req);
        data.putOpt("tvwallid", this.tvwallid);
        String ret = data.toString();
        return ret;
    }

    public CuResponse getResponse() {
        return (CuResponse)new GetTvWallSchemeResponse();
    }

    public String getTvwallid() {
        return this.tvwallid;
    }

    public void setTvwallid(String tvwallid) {
        this.tvwallid = tvwallid;
    }
}
