package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetDumbMuteResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 静音哑音状态获取
 * @author LiPengJia
 * @see GetDumbMuteResponse
 */
public class GetDumbMuteRequest extends MTRequest {
	

	/*{
		“req”:{ “name”:”getdumbmute”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“ismute”:true,
		}*/

	/**
	 * True:静音控制,Fase:哑音控制
	 */
	private String mute;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getdumbmute");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("ismute", mute);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetDumbMuteResponse();
	}

	public String isMute() {
		return mute; 
	}

	public void setMute(String mute) {
		this.mute = mute;
	}

}
