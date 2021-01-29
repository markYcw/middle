package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 7. 透明串口数据通知
 * 
 * @author dengjie
 * 
 */
public class TransCmdNotify extends CuNotify {
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
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		JSONObject channel = jsonData.optJSONObject("channel");
		if (channel != null) {
			this.channelPuid = channel.optInt("puid");
			this.channelChnid = channel.optInt("chnid");
		}
		this.date = jsonData.optString("date");
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
