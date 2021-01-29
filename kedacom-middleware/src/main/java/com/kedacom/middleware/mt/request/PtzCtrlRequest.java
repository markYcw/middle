package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.PtzCtrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ptz控制
 * @author LiPengJia
 * @see PtzCtrlResponse
 */
public class PtzCtrlRequest extends MTRequest {

	/*{
		“req”:{ “name”:”ptzctrl”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“mode”:0
		}*/

	/** 单频双显 */
	public static final int MODE_SINGLE_DOUBLE = 0;
	/** 双频双显 */
	public static final int MODE_DOUBLE_DOUBLE = 1;
	/** 单频三显 */
	public static final int MODE_SINGLE_THRED = 2;
	/**
	 * 模式 {@link #MODE_SINGLE_DOUBLE} / {@link #MODE_DOUBLE_DOUBLE} / {@link #MODE_SINGLE_THRED}
	 */
	private int mode;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("ptzctrl");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("mode", mode);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new PtzCtrlResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
