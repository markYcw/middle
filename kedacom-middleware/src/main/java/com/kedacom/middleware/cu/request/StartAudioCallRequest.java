package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.SetCapsResponse;
import com.kedacom.middleware.cu.response.StartAudioCallResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 9.4 开始语音呼叫(未实现)
 * 
 * @author dengjie
 * @see SetCapsResponse
 */
public class StartAudioCallRequest extends CuRequest {
	/**
	 * 呼叫的编码通道puid
	 */
	private String channelPuid;
	/**
	 * 呼叫的编码通道chnid
	 */
	private int channelChnid;
	/**
	 * 呼叫索引，从mediacu获取(支持端口协商有用)
	 */
	private int index;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("startaudiocall");

		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", channelPuid);
		channel.putOpt("chnid", channelChnid);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("channel", channel);
		data.putOpt("index", index);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StartAudioCallResponse();
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
