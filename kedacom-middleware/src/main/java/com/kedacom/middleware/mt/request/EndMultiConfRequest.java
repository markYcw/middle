package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.EndMultiConfResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 退出多点会议
 * @author LinChaoYu
 * @see EndMultiConfResponse
 */
public class EndMultiConfRequest extends MTRequest {

	@Override
	public String toJson() throws JSONException {
/*		{
			“req”:{ “name”:”endmulticonf”,
			       “ssno”:1,
			       “ssid”:5
			      }
			}

		*/
		//Req部分
		JSONObject req = super.buildReq("endmulticonf");
		
		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new EndMultiConfResponse();
	}

}
