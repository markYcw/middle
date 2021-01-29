package com.kedacom.middleware.svr.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONObject;

/**
 * SVR掉线通知
 * 
 * @author TaoPeng
 * 
 */
public class DownloadrecntyNotify extends SVRNotify {
	/**
	 * 下载句柄标示一个下载操作
	 */
	private int downloadhandle;
	/**
	 * 1:下载进度2:下载完成3:下载失败4:下载超时 (录像下载可能要下载多个录像文件，接收到此事件，不停止下载录像的话，
	 * SDK会继续下载后续录像，除非是最后一段）
	 */
	private int type;
	/**
	 * 进度
	 */
	private int pro;
	public static final String NAME = "downloadrecnty";

	@Override
	public void parseData(JSONObject jsonData) throws DataException {

		super.parseNty(jsonData);
		this.downloadhandle = jsonData.optInt("downloadhandle");
		this.type = jsonData.optInt("type");
		this.pro = jsonData.optInt("pro");

	}

	public int getDownloadhandle() {
		return downloadhandle;
	}

	public void setDownloadhandle(int downloadhandle) {
		this.downloadhandle = downloadhandle;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPro() {
		return pro;
	}

	public void setPro(int pro) {
		this.pro = pro;
	}

}
