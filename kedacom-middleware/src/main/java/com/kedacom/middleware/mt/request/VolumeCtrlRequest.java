package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.VolumeCtrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 音量控制
 * @author LiPengJia
 * @see VolumeCtrlResponse
 */
public class VolumeCtrlRequest extends MTRequest {

	/*{
		“req”:{ “name”:”volumectrl”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		},
		“type”:1
		“volume”:20
		}*/
	/** 扬声器**/
	public static final int PTZCMD_TOP = 1;
	/** 麦克**/
	public static final int PTZCMD_BOTTOM = 2;
	/**
	 * 类型{@link #PTZCMD_TOP} / {@link #PTZCMD_BOTTOM}
	 */
	private int type;
	/**
	 * 音量大小，最大31
	 */
	private int volume;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("volumectrl");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		data.put("volume", volume);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new VolumeCtrlResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
