package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.ListDomainResponse;
import com.kedacom.middleware.cu.response.StopLookTvWallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3. 获取域列表
 * 
 * @author dengjie
 * @see ListDomainResponse
 */
public class StopLookTvWallRequest extends CuRequest {

	//电视墙号
	private String tvwallid;
	
	//电视机号
	private int tvid;
	
	//电视机画面号
	private int divid;
	
	//个人理解：编码器puid
	private String puid;
	
	//编码器通道号
	private int chnid;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("stoplooktvwall");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("tvwallid", tvwallid);
		data.putOpt("tvid", tvid);
		data.putOpt("divid", divid);
		
		JSONObject decchannel = new JSONObject();
		decchannel.putOpt("puid", puid);
		decchannel.putOpt("chnid", chnid);
		
		data.putOpt("encchannel", decchannel);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StopLookTvWallResponse();
	}

	public String getTvwallid() {
		return tvwallid;
	}

	public void setTvwallid(String tvwallid) {
		this.tvwallid = tvwallid;
	}

	public int getTvid() {
		return tvid;
	}

	public void setTvid(int tvid) {
		this.tvid = tvid;
	}

	public int getDivid() {
		return divid;
	}

	public void setDivid(int divid) {
		this.divid = divid;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

}
