package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.domain.Chns;
import com.kedacom.middleware.mcu.response.StartLookTVWallResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 终端开始上墙
 * @see StartLookTVWallResponse
 * @author YueZhipeng
 * 
 * 2018-02-02 LinChaoYu 适配会议平台5.0及以上版本
 *
 */
public class StartLookTVWallRequest extends McuRequest {
	
	/**
	 * 会议e164号
	 */
	private String confe164;
	
	/**
	 * 电视墙ID（会议平台5.0及以上版本接口中电视墙ID为字符串）
	 */
	private String eqpid;
	
	/*
	 * 终端 e164号或IP （会议平台4.7版本有效）
	 */
	private String mtinfo;
	
	/*
	 * 电视墙通道(0~1)（会议平台4.7版本有效）
	 */
	private int chn;
	
	/**
	 * 电视墙模式 
	 * 1-选看；2-四分屏(仅传统会议有效)；3-单通道轮询；
	 */
	private int mode;
	
	/* === 以下两个选看信息（mode为1 有效） === */
	
	/**
	 * 选看类型 
	 * 1-指定；2-发言人跟随；3-主席跟随；4-会议轮询跟随；6-选看画面合成；10-选看双流
	 */
	private int specificMembertype;
	
	/**
	 * 选看画面合成id（仅membertype为 6-选看画面合成 时生效）
	 * 最大字符长度：48个字节
	 */
	private String specificVmpid;
	
	/* === 以下三个个为轮询信息（mode为3 有效） === */
	
	/**
	 * 轮询次数
	 */
	private int pollNum;
	
	/**
	 * 轮询方式 
	 * 1-仅图像；3-音视频轮询；
	 */
	private int pollMode;
	
	/**
	 * 轮询间隔（秒）
	 */
	private int pollKeeptime;
	
	/**
	 * 通道对应的终端信息
	 */
	private List<Chns> chnss;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("startlooktvwall");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);	
		data.put("eqpid", eqpid);	
		data.put("mtinfo", mtinfo);	
		data.put("chn", chn);
		
		data.put("mode", mode);
		
		JSONObject jsonSpecific = new JSONObject();
		jsonSpecific.put("membertype", specificMembertype);
		jsonSpecific.put("vmpid", specificVmpid);
		
		data.put("specific", jsonSpecific);
		
		JSONObject jsonPoll = new JSONObject();
		jsonPoll.put("num", pollNum);
		jsonPoll.put("mode", pollMode);
		jsonPoll.put("keeptime", pollKeeptime);
		
		data.put("poll", jsonPoll);
		
		JSONArray jsonArray = new JSONArray();
		if(chnss != null && chnss.size() > 0){
			for(Chns chns : chnss){
				
				JSONObject jsonChns = new JSONObject();
				jsonChns.put("mt", chns.getMt());
				jsonChns.put("chnidx", chns.getChnidx());
				
				jsonArray.put(jsonChns);
			}
		}
		
		data.put("chns", jsonArray);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartLookTVWallResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getEqpid() {
		return eqpid;
	}

	public void setEqpid(String eqpid) {
		this.eqpid = eqpid;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public int getChn() {
		return chn;
	}

	public void setChn(int chn) {
		this.chn = chn;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getSpecificMembertype() {
		return specificMembertype;
	}

	public void setSpecificMembertype(int specificMembertype) {
		this.specificMembertype = specificMembertype;
	}

	public String getSpecificVmpid() {
		return specificVmpid;
	}

	public void setSpecificVmpid(String specificVmpid) {
		this.specificVmpid = specificVmpid;
	}

	public int getPollNum() {
		return pollNum;
	}

	public void setPollNum(int pollNum) {
		this.pollNum = pollNum;
	}

	public int getPollMode() {
		return pollMode;
	}

	public void setPollMode(int pollMode) {
		this.pollMode = pollMode;
	}

	public int getPollKeeptime() {
		return pollKeeptime;
	}

	public void setPollKeeptime(int pollKeeptime) {
		this.pollKeeptime = pollKeeptime;
	}

	public List<Chns> getChnss() {
		return chnss;
	}

	public void setChnss(List<Chns> chnss) {
		this.chnss = chnss;
	}

}
