package com.kedacom.middleware.mt.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mt.response.PtzCtrlResponse;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Ptz控制
 * @author ycw
 * @Date 2021/4/2 10:03
 * @see PtzCtrlResponse
 */
@Data
public class PtzCtrlRequest extends MTRequest {


	/** 单频双显 */
	public static final int MODE_SINGLE_DOUBLE = 0;
	/** 双频双显 */
	public static final int MODE_DOUBLE_DOUBLE = 1;
	/** 单频三显 */
	public static final int MODE_SINGLE_THRED = 2;
	/**
	 * 模式 {@link #MODE_SINGLE_DOUBLE} / {@link #MODE_DOUBLE_DOUBLE} / {@link #MODE_SINGLE_THRED}
	 */
	/**
	 *True开始相应操作 False停止相应操作
	 */
	private boolean isstart;
	/**
	 * 控制命令
	 * 1:上 2:下
	 * 3:左 4:右
	 * 5:自动聚焦
	 * 6:焦距大 7:焦距小
	 * 8:zoomin 9:zoomout
	 * 10：亮 11：暗
	 */
	private int ptzcmd;

	private boolean isinmeeting = false;

	@Override
	public String toJson() throws JSONException {
		//Req部分
		JSONObject req = super.buildReq("ptzctrl");

		//Data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("isstart", isstart);
		data.put("ptzcmd",ptzcmd);
		data.put("isinmeeting",isinmeeting);
		//返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new PtzCtrlResponse();
	}

}
