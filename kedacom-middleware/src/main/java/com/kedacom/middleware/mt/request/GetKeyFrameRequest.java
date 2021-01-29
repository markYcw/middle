package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetKeyFrameResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求关键帧
 * @author LiPengJia
 * @see GetKeyFrameResponse
 */
public class GetKeyFrameRequest extends MTRequest {

/*	{
		“req”:{ “name”:”getkeyframe”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“type”:1,
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
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getkeyframe");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("type", type);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetKeyFrameResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
