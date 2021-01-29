package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.StopP2PResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 停止点对点会议
 * @author LiPengJia
 * @see StopP2PResponse
 */
public class StopP2PRequest extends MTRequest {

	@Override
	public String toJson() throws JSONException {
/*		{
			“req”:{ “name”:”stopp2p”,
			       “ssno”:1,
			       “ssid”:5
			      }
			}

		*/
		//Req部分
		JSONObject req = super.buildReq("stopp2p");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new StopP2PResponse();
	}

}
