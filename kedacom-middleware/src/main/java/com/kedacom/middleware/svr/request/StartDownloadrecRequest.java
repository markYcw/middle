package com.kedacom.middleware.svr.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.svr.response.StartDownloadrecResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * SVR录像下载
 * 
 * @see StartDownloadrecResponse
 * @author DengJie
 * 
 */
public class StartDownloadrecRequest extends SVRRequest {
	/**
	 * 查询录像的通道id，0表示合成通道
	 */
	private int chnid;
	/**
	 * 开始时间
	 */
	private String starttime;
	/**
	 * 结束时间
	 */
	private String endtime;
	/**
	 * 下载的文件名字不含后缀名(下载的文件会自动在后面补上.mp4)
	 */
	private String downloadfilename;
	/**
	 * 下载的文件目录(要保证路径存在)
	 */
	private String downloadfiledir;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("downloadrec");
		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("chnid", chnid);
		data.put("starttime", starttime);
		data.put("endtime", endtime);
		data.put("downloadfilename", downloadfilename);
		data.put("downloadfiledir", downloadfiledir);

		// 返回
		String ret = data.toString();
		return ret;
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getDownloadfilename() {
		return downloadfilename;
	}

	public void setDownloadfilename(String downloadfilename) {
		this.downloadfilename = downloadfilename;
	}

	public String getDownloadfiledir() {
		return downloadfiledir;
	}

	public void setDownloadfiledir(String downloadfiledir) {
		this.downloadfiledir = downloadfiledir;
	}

	@Override
	public IResponse getResponse() {

		return new StartDownloadrecResponse();
	}

}
