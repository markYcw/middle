package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.StartMediaPlayResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 9.1 开始浏览视频
 * 
 * @author dengjie
 * @see StartMediaPlayResponse
 */
public class StartMediaPlayRequest extends CuRequest {

	/**
	 * 要播放的编码通道
	 */
	private String shPuid;
	/**
	 * 要播放的编码通道
	 */
	private int shChnid;
	/**
	 * 1. 本地播放：puid不用填写，chnid为窗口号(mediacu.ocx获取) 2. 码流上墙: puid
	 * 为解码器串号，chnid为解码器通道号
	 */
	private String dhPuid;
	/**
	 * 1. 本地播放：puid不用填写，chnid为窗口号(mediacu.ocx获取) 2. 码流上墙: puid
	 * 为解码器串号，chnid为解码器通道号
	 */
	private int dhChnid;
	/**
	 * 播放模式 1:视频，2:音频，3:音视频
	 */
	private int mode;
	/**
	 * 传输方式0:udp 1:Tcp
	 */
	private int transtype;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("startmediaplay");

		// srcchannel部分
		JSONObject dstchannel = new JSONObject();
		dstchannel.putOpt("puid", shPuid);
		dstchannel.putOpt("chnid", shChnid);
		// srcchannel部分
		JSONObject srcchannel = new JSONObject();
		srcchannel.putOpt("puid", dhPuid);
		srcchannel.putOpt("chnid", dhChnid);
		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("dstchannel", dstchannel);
		data.putOpt("srcchannel", srcchannel);
		data.putOpt("mode", mode);
		data.putOpt("transtype", transtype);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new StartMediaPlayResponse();
	}

	public String getShPuid() {
		return shPuid;
	}

	public void setShPuid(String shPuid) {
		this.shPuid = shPuid;
	}

	public int getShChnid() {
		return shChnid;
	}

	public void setShChnid(int shChnid) {
		this.shChnid = shChnid;
	}

	public String getDhPuid() {
		return dhPuid;
	}

	public void setDhPuid(String dhPuid) {
		this.dhPuid = dhPuid;
	}

	public int getDhChnid() {
		return dhChnid;
	}

	public void setDhChnid(int dhChnid) {
		this.dhChnid = dhChnid;
	}

	public int getMode() {
		return mode;
	}

	public void setModel(int mode) {
		this.mode = mode;
	}

	public int getTranstype() {
		return transtype;
	}

	public void setTranstype(int transtype) {
		this.transtype = transtype;
	}

}
