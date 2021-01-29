package com.kedacom.middleware.mcu.response;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * 发布直播响应结果
 * 
 * @author LinChaoYu
 *
 */
public class GetRtspUrlResponse extends McuResponse {
	
	/**
	 * RTSP播放路径
	 */
	private String url;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		url = jsonData.optString("url");
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
