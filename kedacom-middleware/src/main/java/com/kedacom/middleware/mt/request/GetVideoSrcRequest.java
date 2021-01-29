package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetvideosrcResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取终端视频源
 * @author LiPengJia
 * @see GetvideosrcResponse
 */
public class GetVideoSrcRequest extends MTRequest {

	/*{
		“req”:{ “name”:”getvideosrc”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“videotype”:0，
		“islocal”:true
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
	 * 本地终端  true 本地终端, false:远端终端（远端只能获取主视频源）
	 */
	private boolean local;
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getvideosrc");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("videotype", videotype);
		data.put("islocal", local);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetvideosrcResponse();
	}

	public int getVideotype() {
		return videotype;
	}

	public void setVideotype(int videotype) {
		this.videotype = videotype;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

}
