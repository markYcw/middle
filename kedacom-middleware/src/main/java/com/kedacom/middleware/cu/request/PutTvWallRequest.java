package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.domain.Binddecs;
import com.kedacom.middleware.cu.domain.TvWall;
import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.ListDomainResponse;
import com.kedacom.middleware.cu.response.PutTvWallResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3. 获取域列表
 * 
 * @author dengjie
 * @see ListDomainResponse
 */
public class PutTvWallRequest extends CuRequest {

	// fase：修改电视墙
	// true: 增加电视墙
	private boolean isadd;

	//电视墙信息
	private TvWall tvWall = new TvWall();

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("puttvwall");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("isadd", this.isadd);

		//binddecs部分
		List<Binddecs> list = tvWall.getList();
		JSONArray binddecsArray = new JSONArray();
		if (list != null && list.size() > 0) {
			for (Binddecs b : list) {

				JSONObject binddecs = new JSONObject();
				binddecs.putOpt("tvid", b.getTvid());
				binddecs.putOpt("divnum", b.getDivnum());

				JSONObject decchanl = new JSONObject();
				decchanl.putOpt("puid", b.getDecchanl().getPuid());
				decchanl.putOpt("chnid", b.getDecchanl().getChnid());
				binddecs.putOpt("decchanl", decchanl);

				binddecsArray.put(binddecs);
			}
		}
		
		//tvwall部分
		JSONObject tvwallJson = new JSONObject();
		tvwallJson.putOpt("id", tvWall.getId());
		tvwallJson.putOpt("name", tvWall.getName());
		tvwallJson.putOpt("domain", tvWall.getDomain());
		tvwallJson.putOpt("tvcount", tvWall.getTvcount());
		tvwallJson.putOpt("style", tvWall.getStyle());
		tvwallJson.putOpt("binddecs", binddecsArray);
	
		data.put("tvwall", tvwallJson);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new PutTvWallResponse();
	}

	public boolean isIsadd() {
		return isadd;
	}

	public void setIsadd(boolean isadd) {
		this.isadd = isadd;
	}

	public TvWall getTvWall() {
		return tvWall;
	}

	public void setTvWall(TvWall tvWall) {
		this.tvWall = tvWall;
	}
}
