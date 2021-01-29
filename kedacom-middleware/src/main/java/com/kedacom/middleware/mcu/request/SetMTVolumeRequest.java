package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.SetMTVolumeResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 调节音量
 * 
 * @see SetMTVolumeResponse
 * @author YueZhipeng
 * 
 */
public class SetMTVolumeRequest extends McuRequest {

	/**
	 * 会议e164号
	 */
	private String confe164;

	/**
	 * 终端IP或者e164号
	 */
	private String mtinfo;

	/**
	 * 音量类型
	 * （4.7接口） 0：输出音量，1：输入
	 * （5.0接口） 1：输出音量，2：输入
	 */

	private int volumetype;

	/**
	 * 音量值 
	 * （4.7接口）Pcmt 0~255, 普通keda终端 0~32 private int volume;
	 * （5.0接口）音量 0-35
	 * 
	 */
	private int volume;

	@Override
	public String toJson() throws JSONException {
		
		// Req部分
		JSONObject req = super.buildReq("setmtvolume");

		// data部分
		JSONObject data = new JSONObject();
		data.put("req", req);
		data.put("confe164", confe164);
		data.put("mtinfo", mtinfo);
		data.put("volumetype", volumetype);
		data.put("volume", volume);

		// 返回
		String ret = data.toString();
		return ret;
	}

	@Override
	public IResponse getResponse() {
		return new SetMTVolumeResponse();
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public int getVolumetype() {
		return volumetype;
	}

	public void setVolumetype(int volumetype) {
		this.volumetype = volumetype;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

}
