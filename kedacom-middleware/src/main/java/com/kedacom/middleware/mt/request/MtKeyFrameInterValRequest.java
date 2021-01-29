package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.MtKeyFrameInterValResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 关键帧间隔设置
 * @author LiPengJia
 * @see MtKeyFrameInterValResponse
 */
public class MtKeyFrameInterValRequest extends MTRequest {

	/*{
		“req”:{ “name”:” mtkeyframeInterval”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“videotype”:1,
		“h264ikeyrate”:3000，
		“ikeyrate”:300
		}*/

	/**
	 * 视频类型  0：主流，1：辅流
	 */
	private int videotype;
	/**
	 * H264最大间隔
	 */
	private int h264ikeyrate;
	/**
	 * 非h264最大间隔
	 */
	private int ikeyrate;
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("mtkeyframeInterval");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("videotype", videotype);
		data.put("h264ikeyrate", h264ikeyrate);
		data.put("ikeyrate", ikeyrate);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new MtKeyFrameInterValResponse();
	}

	public int getVideotype() {
		return videotype;
	}

	public void setVideotype(int videotype) {
		this.videotype = videotype;
	}

	public int getH264ikeyrate() {
		return h264ikeyrate;
	}

	public void setH264ikeyrate(int h264ikeyrate) {
		this.h264ikeyrate = h264ikeyrate;
	}

	public int getIkeyrate() {
		return ikeyrate;
	}

	public void setIkeyrate(int ikeyrate) {
		this.ikeyrate = ikeyrate;
	}

}
