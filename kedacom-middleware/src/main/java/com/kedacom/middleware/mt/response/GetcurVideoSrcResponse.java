package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.GetcurVideoSrcRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：获取当前视频源
 * @author LiPengJia
 * @see GetcurVideoSrcRequest
 */
public class GetcurVideoSrcResponse extends MTResponse {

/*	{
		“resp”:{ “name”:” getcurvideosrc”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		},
		“videoport”:1
		}
*/
	/**
	 * 终端视频端口
	 */
	private int videoport;
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		videoport = jsonData.optInt("videoport");

	}
	public int getVideoport() {
		return videoport;
	}
	public void setVideoport(int videoport) {
		this.videoport = videoport;
	}

}
