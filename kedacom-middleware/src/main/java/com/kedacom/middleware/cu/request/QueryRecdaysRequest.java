package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.QueryRecdaysResponse;
import com.kedacom.middleware.util.DateUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 10.2 录像日历
 * 
 * @author ycw
 * @see QueryRecdaysResponse
 */
public class QueryRecdaysRequest extends CuRequest {

	/**
	 * 1:平台， 2：前端 3：本地
	 */
	private int type;
	/**
	 * 域名称
	 */
	private String domain;
	/**
	 * 开始时间
	 */
	private Date starttime;
	/**
	 * 结束时间
	 */
	private Date endtime;
	/**
	 * 呼叫的编码通道puid
	 */
	private String puid;
	/**
	 * 呼叫的编码通道chnid
	 */
	private int chnid;
	
	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("queryrecdays");
		
		// queryvod部分
		JSONObject queryvod = new JSONObject();
		queryvod.putOpt("domain", domain);
		queryvod.putOpt("starttime", DateUtils.getDateString(starttime));
		queryvod.putOpt("endtime", DateUtils.getDateString(endtime));
		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", puid);
		channel.putOpt("chnid", chnid);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("queryvod", queryvod);
		data.putOpt("channel", channel);
		data.putOpt("type", type);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new QueryRecdaysResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
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
