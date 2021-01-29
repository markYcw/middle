package com.kedacom.middleware.cu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 2. 添加电视墙回复
 * 
 * @author fanshaocong
 * 
 */
public class PutTvWallResponse extends CuResponse {

	/**
	 * 电视墙id
	 */
	private String tvwallid;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.tvwallid = jsonData.optString("tvwallid");

	}

	public String getTvwallid() {
		return tvwallid;
	}

	public void setTvwallid(String tvwallid) {
		this.tvwallid = tvwallid;
	}

}
