package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.GetRecordResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 10.1 获取录像
 * 
 * @author dengjie
 * @see GetRecordResponse
 */
public class GetRecordRequest extends CuRequest {

	/**
	 * 1:平台， 2：前端 3：本地
	 */
	private int type;
	/**
	 * 域名称
	 */
	private String queryvodDomain;
	/**
	 * 开始时间
	 */
	private String queryvodStarttime;
	/**
	 * 结束时间
	 */
	private String queryvodsEndtime;
	/**
	 * 呼叫的编码通道puid
	 */
	private String channelPuid;
	/**
	 * 呼叫的编码通道chnid
	 */
	private int channelChnid;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("getrecord");
		// queryvod部分
		JSONObject queryvod = new JSONObject();
		queryvod.putOpt("domain", queryvodDomain);
		queryvod.putOpt("starttime", queryvodStarttime);
		queryvod.putOpt("endtime", queryvodsEndtime);
		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", channelPuid);
		channel.putOpt("chnid", channelChnid);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("channel", channel);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new GetRecordResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQueryvodDomain() {
		return queryvodDomain;
	}

	public void setQueryvodDomain(String queryvodDomain) {
		this.queryvodDomain = queryvodDomain;
	}

	public String getQueryvodStarttime() {
		return queryvodStarttime;
	}

	public void setQueryvodStarttime(String queryvodStarttime) {
		this.queryvodStarttime = queryvodStarttime;
	}

	public String getQueryvodsEndtime() {
		return queryvodsEndtime;
	}

	public void setQueryvodsEndtime(String queryvodsEndtime) {
		this.queryvodsEndtime = queryvodsEndtime;
	}

	public String getChannelPuid() {
		return channelPuid;
	}

	public void setChannelPuid(String channelPuid) {
		this.channelPuid = channelPuid;
	}

	public int getChannelChnid() {
		return channelChnid;
	}

	public void setChannelChnid(int channelChnid) {
		this.channelChnid = channelChnid;
	}

}
