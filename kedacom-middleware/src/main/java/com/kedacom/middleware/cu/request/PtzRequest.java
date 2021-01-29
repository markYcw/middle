package com.kedacom.middleware.cu.request;

import com.kedacom.middleware.cu.response.CuResponse;
import com.kedacom.middleware.cu.response.PtzResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 9.3 PTZ控制
 * 
 * @author dengjie
 * @see PtzResponse
 * 
 */
public class PtzRequest extends CuRequest {

	/**
	 * 设备ID
	 */
	private String puid;
	/**
	 * 通道号
	 */
	private int chnid;
	/**
	 * 控制指令类型
	 * <pre>
	 * 0 向左,1 向右,2 向上, 3向下,4 左上,5 左下, 6右上,7 右下, 8停止移动,
	 * 9 视野拉近, 10视野拉远, 11视野缩放停止,12 归位, 
	 * 128 开始自动巡航, 129停止自动巡航, 
	 * 210 将焦距调远, 211 将焦距调近, 212 自动调焦, 213 调焦停止,
	 * 214 摄象头预存 预案保存, 215 调摄象头预存 调取预案, 
	 * 216 初始化摄像头, 217 画面调亮, 218 画面调暗, 219 停止调亮, 
	 * 220 新命令, 221 附加命令, 
	 * 222 中心定位, 223 局部放大, 224 艾立克水平移动速度设置命令,,225 艾立克垂直移动速度设置命令，226 sony亮度调节启动命令
	 * </pre>
	 */
	private int ptzCmd;
	/**
	 * 步长1~15
	 */
	private int ptzRange;
	/**
	 * 级别
	 */
	private int ptzLevel;
	/**
	 * 占用时间，1~30秒，默认0
	 */
	private int ptzHoldtimer;

	@Override
	public String toJson() throws JSONException {
		// Req部分
		JSONObject req = super.buildReq("ptz");

		// channel部分
		JSONObject channel = new JSONObject();
		channel.putOpt("puid", puid);
		channel.putOpt("chnid", chnid);

		// ptz部分
		JSONObject ptz = new JSONObject();
		ptz.putOpt("cmd", ptzCmd);
		ptz.putOpt("range", ptzRange);
		ptz.putOpt("level", ptzLevel);
		ptz.putOpt("holdtimer", ptzHoldtimer);

		// data部分
		JSONObject data = new JSONObject();
		data.putOpt("req", req);
		data.putOpt("channel", channel);
		data.putOpt("ptz", ptz);
		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public CuResponse getResponse() {
		return new PtzResponse();
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

	public int getChnid() {
		return chnid;
	}

	public void setChnid(int chnid) {
		this.chnid = chnid;
	}

	public int getPtzCmd() {
		return ptzCmd;
	}

	public void setPtzCmd(int ptzCmd) {
		this.ptzCmd = ptzCmd;
	}

	public int getPtzRange() {
		return ptzRange;
	}

	public void setPtzRange(int ptzRange) {
		this.ptzRange = ptzRange;
	}

	public int getPtzLevel() {
		return ptzLevel;
	}

	public void setPtzLevel(int ptzLevel) {
		this.ptzLevel = ptzLevel;
	}

	public int getPtzHoldtimer() {
		return ptzHoldtimer;
	}

	public void setPtzHoldtimer(int ptzHoldtimer) {
		this.ptzHoldtimer = ptzHoldtimer;
	}

}
