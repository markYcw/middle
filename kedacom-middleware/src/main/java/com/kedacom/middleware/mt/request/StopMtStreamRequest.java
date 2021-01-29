package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.StopMtStreamResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止浏览码流
 * @author LiPengJia
 * @see StopMtStreamResponse
 */
public class StopMtStreamRequest extends MTRequest {

	/*{
		“req”:{ “name”:”stopmtstream”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“type”:1,
		“ipaddr”:{“ip”:””,”port”:222}
		}*/

	/** 本地**/
	public static final int TYPE_LOCAL = 0;
	/** 远端**/
	public static final int TYPE_REMOTE = 1;
	/** 本地视频到任意地址**/
	public static final int TYPE_LOCAL_video = 2;
	/** 远端视频到任意地址**/
	public static final int TYPE_REMOTE_video = 3;
	/** 本地音频到任意地址**/
	public static final int TYPE_LOCAL_audio = 4;
	/** 远端音频到任意地址**/
	public static final int TYPE_REMOTE_audio = 5;
	/** 本地双流到任意地址**/
	public static final int TYPE_LOCAL_DOUBLE = 6;
	/** 远端双流到任意地址**/
	public static final int TYPE_REMOTE_DOUBLE = 7;
	/**
	 * 码流类型 {@link #TYPE_LOCAL} / {@link #TYPE_REMOTE} / {@link #TYPE_LOCAL_video} / {@link #TYPE_REMOTE_video} 
	 * / {@link #TYPE_LOCAL_audio} / {@link #TYPE_REMOTE_audio} / {@link #TYPE_LOCAL_DOUBLE} / {@link #TYPE_REMOTE_DOUBLE}
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
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("stopmtstream");

		//Data部分
		JSONObject ipaddr = new JSONObject();
		ipaddr.put("ip", ip);
		ipaddr.put("port", port);
		
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		data.put("ipaddr", ipaddr);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopMtStreamResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
