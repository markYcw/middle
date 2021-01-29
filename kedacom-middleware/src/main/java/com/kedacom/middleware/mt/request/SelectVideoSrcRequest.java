package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.SelectVideoSrcResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 选择视频源
 * @author LiPengJia
 * @see SelectVideoSrcResponse
 */
public class SelectVideoSrcRequest extends MTRequest {

	/*{
		“req”:{ “name”:”selectvideosrc”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“videotype”:0，
		“videoport”:1
		}*/

	/**
	 * 0.主视频源
	 */
	public static final int VIDEOTYPE_MAIN = 0;
	/**
	 *  1.辅视频源
	 */
	public static final int VIDEOTYPE_AUXILIARY = 1;
	
	/**
	 * 视频源类型 {@link #VIDEOTYPE_MAIN} {@link #VIDEOTYPE_AUXILIARY}
	 */
	private int videotype; 
	/**
	 * 终端视频端口
	 */
	private int videoport;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("selectvideosrc");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("videotype", videotype);
		data.put("videoport", videoport);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new SelectVideoSrcResponse();
	}

	public int getVideotype() {
		return videotype;
	}

	public void setVideotype(int videotype) {
		this.videotype = videotype;
	}

	public int getVideoport() {
		return videoport;
	}

	public void setVideoport(int videoport) {
		this.videoport = videoport;
	}

}
