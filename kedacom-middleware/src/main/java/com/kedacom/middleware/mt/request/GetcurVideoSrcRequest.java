package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.GetcurVideoSrcResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取当前视频源
 * @author LiPengJia
 * @author ycw alter 2021/4/2
 * @see GetcurVideoSrcResponse
 */
@Data
public class GetcurVideoSrcRequest extends MTRequest {


	/**
	 * true:本地终端，false：远端终端.
	 */
	private boolean islocal;

	private int videoType;
	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("getcurvideosrc");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("islocal", islocal);
		data.put("videotype",videoType);
				
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new GetcurVideoSrcResponse();
	}

}
