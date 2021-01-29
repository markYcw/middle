package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.SetCapsResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3. 设置能力集
 * 
 * @author dengjie
 * @see SetCapsResponse
 */
public class SetCapsRequest extends CuRequest {

	/**
	 * 是否能处理异域平台掉线通知。 1：能， 0：不能
	 * 如果填写0，当下级平台掉线时，会收到下级平台所有设备掉线通知。如果填写1，只会有一个平台掉线通知。
	 */
	private int canproccmudis;
	/**
	 * 最大视频浏览路数
	 */
	private int maxexporenum;
	/**
	 * 最大放像路数
	 */
	private int maxplayrecnum;
	/**
	 * 最大语音呼叫路数（一个客户端目前同时只支持呼叫一个）
	 */
	private int maxaudiocallnum;
	/**
	 * 客户端是否支持端口协商（语音呼叫用） 1：能， 0：不能
	 */
	private int supportconsult;

	// maxexporenum+maxplayrecnum+maxaudiocallnum 不能大于 254

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("setcaps");

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("canproccmudis", canproccmudis);
		data.putOpt("maxexporenum", maxexporenum);
		data.putOpt("maxplayrecnum", maxplayrecnum);
		data.putOpt("maxaudiocallnum", maxaudiocallnum);
		data.putOpt("supportconsult", supportconsult);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new SetCapsResponse();
	}

	public int getCanproccmudis() {
		return canproccmudis;
	}

	public void setCanproccmudis(int canproccmudis) {
		this.canproccmudis = canproccmudis;
	}

	public int getMaxexporenum() {
		return maxexporenum;
	}

	public void setMaxexporenum(int maxexporenum) {
		this.maxexporenum = maxexporenum;
	}

	public int getMaxplayrecnum() {
		return maxplayrecnum;
	}

	public void setMaxplayrecnum(int maxplayrecnum) {
		this.maxplayrecnum = maxplayrecnum;
	}

	public int getMaxaudiocallnum() {
		return maxaudiocallnum;
	}

	public void setMaxaudiocallnum(int maxaudiocallnum) {
		this.maxaudiocallnum = maxaudiocallnum;
	}

	public int getSupportconsult() {
		return supportconsult;
	}

	public void setSupportconsult(int supportconsult) {
		this.supportconsult = supportconsult;
	}

}
