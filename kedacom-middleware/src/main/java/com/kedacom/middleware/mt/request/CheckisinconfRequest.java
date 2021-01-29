package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.CheckisinconfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 终端是否在会议中
 * @see CheckisinconfResponse
 * @author LiPengJia
 * @see CheckisinconfResponse
 */
public class CheckisinconfRequest extends MTRequest {

	/*{
		“req”:{ “name”:”checkisinconf”,
		       “ssno”:1,
		       “ssid”:5
		 },
		“type”:1,
		}*/

	/**
	 * 1.是否在点对点会议
	 */
	public static final int TYPE_P2P = 1;
	/**
	 * 2.是否在多点会议
	 */
	public static final int TYPE_MP = 2;
	/**
	 *  3.是否在会议中（不区分点对点还是多点。是1或者2都算）
	 */
	public static final int TYPE_P2P_MP = 3;
	
	/**
	 * 类型 {@link #TYPE_P2P} / {@link #TYPE_MP} / {@link #TYPE_P2P_MP}
	 */
	private int type;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("checkisinconf");

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
		return new CheckisinconfResponse();
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
