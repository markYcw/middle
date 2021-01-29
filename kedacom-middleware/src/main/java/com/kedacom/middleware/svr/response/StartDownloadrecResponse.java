package com.kedacom.middleware.svr.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.svr.request.StartDownloadrecRequest;
import org.json.JSONObject;

/**
 * SVR录像下载
 * 
 * @see StartDownloadrecRequest
 * @author DengJie
 * 
 */
public class StartDownloadrecResponse extends SVRResponse {
	/**
	 * 
	 */
	private Integer downloadhandle;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		this.downloadhandle = jsonData.optInt("downloadhandle");
	}

	public Integer getDownloadhandle() {
		return downloadhandle;
	}

	public void setDownloadhandle(Integer downloadhandle) {
		this.downloadhandle = downloadhandle;
	}

}
