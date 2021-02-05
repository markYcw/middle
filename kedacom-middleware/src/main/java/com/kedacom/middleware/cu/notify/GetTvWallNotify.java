package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.cu.domain.Binddecs;
import com.kedacom.middleware.cu.domain.DecChn;
import com.kedacom.middleware.cu.domain.TvWall;
import com.kedacom.middleware.exception.DataException;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取电视墙信息
 *
 * @author dengjie
 */
@Data
public class GetTvWallNotify extends CuNotify {

    /**
     * 命令值
     */
    public static final String NAME = "gettvwall";

    /**
     * 电视墙
     */
    public static List<TvWall> tvWalls = new ArrayList<TvWall>();


    /**
     * 是否传完
     */
    private boolean isend;

    @Override
    public void parseData(JSONObject jsonData) throws DataException {

        super.parseNty(jsonData);
			this.isend = jsonData.optBoolean("isend");
			JSONObject object = jsonData.optJSONObject("tvwall");
			if (object != null) {
				this.parseData_tvwall(object);
			}
    }

    private void parseData_tvwall(JSONObject jsonObj) {

        String id = jsonObj.optString("id");
        String name = jsonObj.optString("name");
        String domain = jsonObj.optString("domain");
        int tvcount = jsonObj.optInt("tvcount");
        int style = jsonObj.optInt("style");
        JSONArray decs = jsonObj.optJSONArray("binddecs");
        List<Binddecs> ds = new ArrayList<Binddecs>();
        if (decs != null && decs.length() > 0) {
            for (int i = 0; i < decs.length(); i++) {
                Binddecs decoder = new Binddecs();
                JSONObject jsonDec = decs.optJSONObject(i);
                int tvid = jsonDec.optInt("tvid");
                int divnum = jsonDec.optInt("divnum");
                JSONObject decchanl = jsonDec.optJSONObject("decchanl");
                DecChn chn = new DecChn();
                chn.setPuid(decchanl.optString("puid"));
                chn.setChnid(decchanl.optInt("chnid"));
                decoder.setTvid(tvid);
                decoder.setDivnum(divnum);
                decoder.setDecchanl(chn);
                ds.add(decoder);
            }
        }

        TvWall tvwall = new TvWall();
        tvwall.setDomain(domain);
        tvwall.setId(id);
        tvwall.setList(ds);
        tvwall.setName(name);
        tvwall.setStyle(style);
        tvwall.setTvcount(tvcount);
        tvWalls.add(tvwall);
    }

    public boolean isIsend() {
        return isend;
    }

}
