package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.MtStarteDualResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 双流发送控制
 * @author LiPengJia
 * @see MtStarteDualResponse
 */
public class MtStarteDualRequest extends MTRequest {

/*	{
		“req”:{ “name”:”mtstartedual”,
		       “ssno”:1,
		       “ssid”:5
		      }，
		“isstart”:true,
		“islocal”:treu
		}*/

	/**
	 * 开始发送还是停止
	 */
	private boolean start;
	
	/**
	 * 是否是本地终端
	 */
	private boolean local;
	
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("mtstartedual");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("isstart", String.valueOf(start));
		data.put("islocal", String.valueOf(local));
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new MtStarteDualResponse();
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

}
