package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.StopMsgResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

/**
 * 停止短消息（字幕）
 * @see StopMsgResponse
 * @author LinChaoYu
 *
 */
public class StopMsgRequest extends McuRequest {
	
	/**
	 * 会议E164号
	 */
	private String confe164;
	
	/**
	 * 收到消息的终端数
	 */
	private int mtnum;
	
	/**
	 * 终端列表
	 */
	private Set<String> mtinfos;
	
	@Override
	public String toJson() throws JSONException {

		//Req部分
		JSONObject req = super.buildReq("stopmsg");
				
		//data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtnum", mtnum);
		data.putOpt("mtinfos", mtinfos);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopMsgResponse();
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

	public Set<String> getMtinfos() {
		return mtinfos;
	}

	public void setMtinfos(Set<String> mtinfos) {
		this.mtinfos = mtinfos;
	}
}
