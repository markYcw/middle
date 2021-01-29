package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetcurVideoSrcResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取当前视频源
 * @author LiPengJia
 * @see GetcurVideoSrcResponse
 */
public class GetcurVideoSrcRequest extends MTRequest {

/*	Json格式
	{
	“req”:{ “name”:”getcurvideosrc”,
	       “ssno”:1,
	       “ssid”:5
	      }，
	 “islocal”:true
	}*/

	/**
	 * true:本地终端，false：远端终端.
	 */
	private boolean local;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getcurvideosrc");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("local", local);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetcurVideoSrcResponse();
	}

	public boolean isLocal() {
		return local;
	}

	public void setLocal(boolean local) {
		this.local = local;
	}

}
