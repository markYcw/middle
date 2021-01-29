package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.SetPipModeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设置画面显示模式
 * @author LiPengJia
 * @see SetPipModeResponse
 */
public class SetPipModeRequest extends MTRequest {

	/*{
		“req”:{ “name”:”setpipmode”,
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
		JSONObject req = super.buildReq("setpipmode");

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
		return new SetPipModeResponse();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

}
