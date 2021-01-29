package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.StartMtStreamResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 开始浏览码流
 * 
 * @author LiPengJia
 * @see StartMtStreamResponse
 */
public class StartMtStreamRequest extends MTRequest {

	/*
	 * { “req”:{ “name”:”startmtstream”, “ssno”:1, “ssid”:5 }， “type”:1,
	 * “ipaddr”:{“ip”:””,”port”:222}, “Isusevtdu”:true }
	 */

	/** 本地 **/
	public static final int TYPE_LOCAL = 0;
	/** 远端 **/
	public static final int TYPE_REMOTE = 1;
	/** 本地视频到任意地址 **/
	public static final int TYPE_LOCAL_VIDEO = 2;
	/** 远端视频到任意地址 **/
	public static final int TYPE_REMOTE_VIDEO = 3;
	/** 本地音频到任意地址 **/
	public static final int TYPE_LOCAL_AUDIO = 4;
	/** 远端音频到任意地址 **/
	public static final int TYPE_REMOTE_AUDIO = 5;
	/** 本地双流到任意地址 **/
	public static final int TYPE_LOCAL_DOUBLE = 6;
	/** 远端双流到任意地址 **/
	public static final int TYPE_REMOTE_DOUBLE = 7;
	/**
	 * 码流类型 {@link #TYPE_LOCAL} / {@link #TYPE_REMOTE} /
	 * {@link #TYPE_LOCAL_VIDEO} / {@link #TYPE_REMOTE_VIDEO} /
	 * {@link #TYPE_LOCAL_AUDIO} / {@link #TYPE_REMOTE_AUDIO} /
	 * {@link #TYPE_LOCAL_DOUBLE} / {@link #TYPE_REMOTE_DOUBLE}
	 */
	private int type;
	/**
	 * 接受码流IP地址
	 */
	private String ip;
	/**
	 * 接受码流端口
	 */
	private int port;
	/**
	 * 是否通过中间件转发
	 */
	private boolean isusevtdu;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("startmtstream");

		// Data部分
		JSONObject ipaddr = new JSONObject();
		ipaddr.put("ip", ip);
		ipaddr.put("port", port);

		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		data.put("ipaddr", ipaddr);
		data.put("isusevtdu", String.valueOf(isusevtdu));

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StartMtStreamResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isIsusevtdu() {
		return isusevtdu;
	}

	public void setIsusevtdu(boolean isusevtdu) {
		this.isusevtdu = isusevtdu;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
