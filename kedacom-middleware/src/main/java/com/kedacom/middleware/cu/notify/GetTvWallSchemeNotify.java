package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.*;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取电视墙预案
 *
 * @author ycw
 */
@Data
public class GetTvWallSchemeNotify extends CuNotify {
    /**
     * 命令值
     */
    public static final String NAME = "gettvwallscheme";

    /**
     * 电视墙预案集
     */
    public static List<TvWallScheme> tvWallSchemes=new ArrayList<TvWallScheme>();

    /**
     * 是否传完
     */
    private boolean isend;


    @Override
    public void parseData(JSONObject jsonData) throws DataException {
        super.parseNty(jsonData);
            this.isend = jsonData.optBoolean("isend");
            JSONObject object = jsonData.optJSONObject("tvwallscheme");
            if (object != null) {
                this.parseData_tvwallscheme(object);
            }
    }

    private void parseData_tvwallscheme(JSONObject jsonObj) {
        int id = jsonObj.optInt("id");
        String client = jsonObj.optString("client");
        int type = jsonObj.optInt("type");
        String name = jsonObj.optString("name");
        String tvwallid = jsonObj.optString("tvwallid");
        JSONArray decs = jsonObj.optJSONArray("schemes");
        ArrayList<Schemes> schemes = new ArrayList<Schemes>();
        if (decs != null && decs.length() > 0) {
            for (int i = 0; i < decs.length(); i++) {
                Schemes decoder = new Schemes();
                JSONObject jsonDec = decs.optJSONObject(i);
                int tvid = jsonDec.optInt("tvid");
                String decpuid = jsonDec.optString("decpuid");
                int stype = jsonDec.optInt("stype");
                JSONArray divs = jsonDec.optJSONArray("divs");
                ArrayList<Divs> divsArrayList = new ArrayList<Divs>();
                if (divs != null && divs.length() > 0) {
                    for (int j = 0; j < divs.length(); j++) {
                        Divs divsInside = new Divs();
                        JSONObject jsonObject = divs.optJSONObject(j);
                        int chnid = jsonObject.optInt("chnid");
                        JSONArray polls = jsonObject.optJSONArray("polls");
                        ArrayList<Polls> pollsArrayList = new ArrayList<Polls>();
                        if (polls != null && polls.length() > 0) {
                            for (int k = 0; k < polls.length(); k++) {
                                JSONObject jsonObjectPoll = polls.optJSONObject(k);
                                Polls pollsInside = new Polls();
                                int dtn = jsonObjectPoll.optInt("dtn");
                                JSONObject EncChNl = jsonObjectPoll.optJSONObject("encchnl");
                                Encchnl encchnl = new Encchnl();
                                encchnl.setChnid(EncChNl.optInt("chnid"));
                                encchnl.setPuid(EncChNl.optString("puid"));
                                pollsInside.setDtn(dtn);
                                pollsInside.setEncchnl(encchnl);
                                pollsArrayList.add(pollsInside);
                            }
                        }
                        divsInside.setChnid(chnid);
                        divsInside.setPolls(pollsArrayList);
                        divsArrayList.add(divsInside);
                    }
                }
                decoder.setTvid(tvid);
                decoder.setDecpuid(decpuid);
                decoder.setStype(stype);
                decoder.setDivs(divsArrayList);
                schemes.add(decoder);
            }
        }
        TvWallScheme tvWallScheme = new TvWallScheme();
        tvWallScheme.setId(id);
        tvWallScheme.setClient(client);
        tvWallScheme.setType(type);
        tvWallScheme.setName(name);
        tvWallScheme.setTvwallid(tvwallid);
        tvWallScheme.setSchemes(schemes);
        tvWallSchemes.add(tvWallScheme);
    }

    public boolean isIsend() {
        return isend;
    }
}
