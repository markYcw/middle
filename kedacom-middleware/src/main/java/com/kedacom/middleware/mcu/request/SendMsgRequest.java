package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.SendMsgResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * 发送短消息（字幕）
 * @see SendMsgResponse
 * @author LinChaoYu
 *
 */
public class SendMsgRequest extends McuRequest {
	
	/**
	 * 会议E164号
	 */
	private String confe164;
	
	/**
	 * 收到消息的终端数
	 */
	private int mtnum;
	
	/**
	 * 消息内容
	 */
	private String content;
	
	/**
	 * 消息类型 0：短消息 1：翻页字幕 2：滚动字幕 3：静态文本（4.7接口）
	 * 消息类型 0-自右至左滚动 1-翻页滚动 2-全页滚动(5.0接口)
	 */
	private int type;
	
	/**
	 * 滚动速率 1：最慢 2：稍慢 3：中速 4：稍快 5：最快（4.7接口）
	 * 滚动速度 1-慢速 2-中速 3-快速(5.0接口)
	 */
	private int rate;
	
	/**
	 * 滚动次数
	 */
	private int times;
	
	/**
	 * 终端列表
	 */
	private Set<String> mtinfos;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("sendmsg");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtnum", mtnum);
		data.putOpt("content", content);
		data.put("type", type);
		data.put("rate", rate);
		data.put("times", times);
		data.putOpt("mtinfos", mtinfos);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new SendMsgResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public int getMtnum() {
		return mtnum;
	}

	public void setMtnum(int mtnum) {
		this.mtnum = mtnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public Set<String> getMtinfos() {
		return mtinfos;
	}

	public void setMtinfos(Set<String> mtinfos) {
		this.mtinfos = mtinfos;
	}
}
