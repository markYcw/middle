package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.SetDumbMuteResponse;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 静音哑音设置
 * @author LiPengJia
 * @see SetDumbMuteResponse
 */
public class SetDumbMuteRequest extends MTRequest {

	/*{
		“req”:{ “name”:”setdumbmute”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“ismute”:true,//修改为字符串
		“isopen”:true
		}*/

	/**
	 * True:静音控制,Fase:哑音控制
	 */
	private String mute;
	/**
	 * 静音/哑音 开关
	 */
	private String open;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("setdumbmute");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("ismute", mute);
		data.put("isopen", open);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new SetDumbMuteResponse();
	}

	public String isMute() {
		return mute;
	}

	public void setMute(String mute) {
		this.mute = mute;
	}

	public String isOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

}
