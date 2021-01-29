package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StopLookTvWallResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadTvWallSchemeRequest extends CuRequest {
    private String tvwallid;

    private String name;

    public String toJson() throws JSONException {
        JSONObject req = buildReq("loadtvwallscheme");
        JSONObject data = new JSONObject();
        data.putOpt("req", req);
        data.putOpt("name", this.name);
        String ret = data.toString();
        return ret;
    }

    public CuResponse getResponse() {
        return (CuResponse)new StopLookTvWallResponse();
    }

    public String getTvwallid() {
        return this.tvwallid;
    }

    public void setTvwallid(String tvwallid) {
        this.tvwallid = tvwallid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
