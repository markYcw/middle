package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StopAudioCallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 9.5 停止语音呼叫(未实现)
 * 
 * @author dengjie
 * @see StopAudioCallResponse
 */
public class StopAudioCallRequest extends CuRequest {

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
		JSONObject req = super.buildReq("stopaudiocall");

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
		return new StopAudioCallResponse();
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
