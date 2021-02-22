package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.domain.Divs;
import com.kedacom.middleware.cu.domain.Polls;
import com.kedacom.middleware.cu.domain.Schemes;
import com.kedacom.middleware.cu.domain.TvWallScheme;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.PutTvWallSchemeResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 添加/修改电视墙预案
 * @author ycw
 * date 2021/2/18
 */
public class PutTvWallSchemeRequest extends CuRequest{
    // fase：修改电视墙预案
    // true: 增加电视墙预案
    private boolean isadd;

    //电视墙预案信息
    private TvWallScheme tvWallScheme = new TvWallScheme();

    @Override
    public String toJson() throws JSONException {
        // Req部分
        JSONObject req = super.buildReq("puttvwallscheme");

        // data部分
        JSONObject data = new JSONObject();
        data.putOpt("req", req);
        data.putOpt("isadd", this.isadd);
        //scheme部分
        List<Schemes> list = tvWallScheme.getSchemes();
        JSONArray schemesArray = new JSONArray();
        if(list != null && list.size() >0){
            for(Schemes s : list){
                JSONObject schemes = new JSONObject();
                schemes.putOpt("tvid",s.getTvid());
                schemes.putOpt("decpuid",s.getDecpuid());
                schemes.putOpt("stype",s.getStype());
                JSONArray divsArray = new JSONArray();
                List<Divs> divsList = s.getDivs();
               if(divsList != null && divsList.size() >0){
                   for(Divs d : divsList){
                       JSONObject divs = new JSONObject();
                       divs.putOpt("chnid",d.getChnid());
                       List<Polls> pollsList = d.getPolls();
                       JSONArray pollsArray = new JSONArray();
                       if(pollsList != null && pollsList.size()>0){
                           for(Polls p : pollsList){
                               JSONObject polls = new JSONObject();
                               polls.putOpt("dtn",p.getDtn());
                               JSONObject encchnl = new JSONObject();
                               encchnl.putOpt("puid",p.getEncchnl().getPuid());
                               encchnl.putOpt("chnid",p.getEncchnl().getChnid());
                               polls.putOpt("encchnl",encchnl);
                               pollsArray.put(polls);
                           }
                       }
                       divs.putOpt("polls",pollsArray);
                       divsArray.put(divs);
                   }
               }
               schemes.putOpt("divs",divsArray);
               schemesArray.put(schemes);
            }
        }

        //tvWallScheme部分
        JSONObject tvWallSchemeJson = new JSONObject();
        tvWallSchemeJson.putOpt("id",tvWallScheme.getId());
        tvWallSchemeJson.putOpt("client",tvWallScheme.getClient());
        tvWallSchemeJson.putOpt("type",tvWallScheme.getType());
        tvWallSchemeJson.putOpt("name",tvWallScheme.getName());
        tvWallSchemeJson.putOpt("tvwallid",tvWallScheme.getTvwallid());
        tvWallSchemeJson.putOpt("schemes",schemesArray);

        data.put("tvWallScheme",tvWallSchemeJson);
        //返回
        String ret = data.toString();
        return ret;
    }

    @Override
    public CuResponse getResponse() {
        return new PutTvWallSchemeResponse();
    }

    public boolean isIsadd() {
        return isadd;
    }

    public void setIsadd(boolean isadd) {
        this.isadd = isadd;
    }

    public TvWallScheme getTvWallScheme() {
        return tvWallScheme;
    }

    public void setTvWallScheme(TvWallScheme tvWallScheme) {
        this.tvWallScheme = tvWallScheme;
    }
}
