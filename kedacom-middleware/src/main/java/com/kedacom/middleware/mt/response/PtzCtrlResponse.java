package com.kedacom.middleware.mt.response;

import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.mt.request.PtzCtrlRequest;
import org.json.JSONObject;

/**
 * 终端响应（Response）：Ptz控制.
 * @author LiPengJia
 * @see PtzCtrlRequest
 */
public class PtzCtrlResponse extends MTResponse {

	/*{
		“resp”:{ “name”:” ptzctrl”,
		      “ssno”:1,
		      “ssid”:5，
		      “errorcode”:0
		},
		“isstart”:true,
		“ptzcmd”:1
		}*/

	/** 上**/
	public static final int PTZCMD_TOP = 1;
	/** 下**/
	public static final int PTZCMD_BOTTOM = 2;
	/** 左**/
	public static final int PTZCMD_LEFT = 3;
	/** 右**/
	public static final int PTZCMD_RIGHT = 4;
	/** 自动聚焦**/
	public static final int PTZCMD_AUTO_FOCUS = 5;
	/** 焦距大**/
	public static final int PTZCMD_FOCUS_BIG = 6;
	/** 焦距小**/
	public static final int PTZCMD_FOCUS_SMALL = 7;
	/** zoomin**/
	public static final int PTZCMD_ZOOMIN = 8;
	/** zoomout**/
	public static final int PTZCMD_ZOOOUT = 9;
	/** 亮**/
	public static final int PTZCMD_LIGHT = 10;
	/** 暗**/
	public static final int PTZCMD_DARK = 11;
	/**
	 * 控制命令{@link #PTZCMD_TOP} / {@link #PTZCMD_BOTTOM} / {@link #PTZCMD_LEFT} / {@link #PTZCMD_RIGHT} 
	 * / {@link #PTZCMD_AUTO_FOCUS} / {@link #PTZCMD_FOCUS_BIG} / {@link #PTZCMD_FOCUS_SMALL} / {@link #PTZCMD_ZOOMIN}
	 * / {@link #PTZCMD_ZOOOUT} / {@link #PTZCMD_LIGHT} / {@link #PTZCMD_DARK}
	 */
	private int ptzcmd;
	/**
	 * true:开始相应操作，false：停止响相应操作
	 */
	private boolean start;
	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseResp(jsonData);
		ptzcmd = jsonData.optInt("ptzcmd");
		start = jsonData.optBoolean("start");
		
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public int getPtzcmd() {
		return ptzcmd;
	}
	public void setPtzcmd(int ptzcmd) {
		this.ptzcmd = ptzcmd;
	}

}
