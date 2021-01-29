package com.kedacom.middleware.mcu.request;

import com.kedacom.middleware.client.IResponse;
import com.kedacom.middleware.mcu.response.GetRtspUrlResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 发布直播
 * 
 * @author LinChaoYu
 *
 */
public class GetRtspUrlRequest extends McuRequest{
	
	/**
	 * 会议平台类型：DeviceType.MCU 或 DeviceType.MCU5
	 */
	private int devtype;
	
	/**
	 * 会议E164号
	 */
	private String confe164;
	
	/**
	 * 终端入会标识
	 * 终端id(如果type为1时有效必填)
	 */
	private String mtid;
	
	/**
	 * 1:终端音视频 2:合成画面音视频
	 */
	private int type;
	
	/**
	 * 会议平台IP地址
	 */
	private String mcuIp;
	
	/**
	 * 会议平台端口
	 */
	private int mcuPort;
	
	/**
	 * 会议平台-用户名
	 */
	private String mcuUsername;
	
	/**
	 * 会议平台-密码
	 */
	private String mcuPassword;
	
	/**
	 * 会议平台-许可证
	 */
	private String mcuKey;
	
	/**
	 * 会议平台-校验码
	 */
	private String mcuSecret;
	
	
	@Override
	public String toJson() throws JSONException {
		
		//Req部分
		JSONObject req = super.buildReq("getrtspurl");
		
		//Data部分
		JSONObject json = new JSONObject();
		json.put("req", req);
		json.put("devtype", devtype);
		json.put("confe164", confe164);
		json.put("mtinfo", mtid);
		json.put("type", type);
		json.put("ip", mcuIp);
		json.put("port", mcuPort);
		json.put("user", mcuUsername);
		json.put("pwd", mcuPassword);
		json.put("key", mcuKey);
		json.put("secret", mcuSecret);
		
		//返回
		String str = json.toString();
		return str;
	}

	@Override
	public IResponse getResponse() {
		return new GetRtspUrlResponse();
	}

	public int getDevtype() {
		return devtype;
	}

	public void setDevtype(int devtype) {
		this.devtype = devtype;
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public String getMtid() {
		return mtid;
	}

	public void setMtid(String mtid) {
		this.mtid = mtid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMcuIp() {
		return mcuIp;
	}

	public void setMcuIp(String mcuIp) {
		this.mcuIp = mcuIp;
	}

	public int getMcuPort() {
		return mcuPort;
	}

	public void setMcuPort(int mcuPort) {
		this.mcuPort = mcuPort;
	}

	public String getMcuUsername() {
		return mcuUsername;
	}

	public void setMcuUsername(String mcuUsername) {
		this.mcuUsername = mcuUsername;
	}

	public String getMcuPassword() {
		return mcuPassword;
	}

	public void setMcuPassword(String mcuPassword) {
		this.mcuPassword = mcuPassword;
	}

	public String getMcuKey() {
		return mcuKey;
	}

	public void setMcuKey(String mcuKey) {
		this.mcuKey = mcuKey;
	}

	public String getMcuSecret() {
		return mcuSecret;
	}

	public void setMcuSecret(String mcuSecret) {
		this.mcuSecret = mcuSecret;
	}
}
