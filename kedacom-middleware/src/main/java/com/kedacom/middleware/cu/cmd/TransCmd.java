package com.kedacom.middleware.cu.cmd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 6. 透明串口命令(暂未实现)
 * 
 * @author dengjei
 * 
 */
public class TransCmd extends CuCommand {
	/**
	 * 命令值
	 */
	public static final String NAME = "transcmd";
	/**
	 * 前端
	 */
	private int channelPuid;
	/**
	 * 前端
	 */
	private int channelChnid;
	/**
	 * 串口数据， 二进制直接转成字符串。
	 */
	private String date;

	@Override
	public String toJson() throws JSONException {
		// cmd部分
		JSONObject cmd = super.buildCmd(NAME);
		// cmd部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", channelPuid);
		channel.putOpt("chnid", channelChnid);
		// Data部分
		JSONObject json = new JSONObject();
		json.put("cmd", cmd);
		json.put("channel", channel);
		json.put("date", date);
		// 返回
		String str = json.toString();
		return str;
	}

	public int getChannelPuid() {
		return channelPuid;
	}

	public void setChannelPuid(int channelPuid) {
		this.channelPuid = channelPuid;
	}

	public int getChannelChnid() {
		return channelChnid;
	}

	public void setChannelChnid(int channelChnid) {
		this.channelChnid = channelChnid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
