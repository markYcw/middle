package com.kedacom.middleware.cu.notify;

import com.kedacom.middleware.exception.DataException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 8.4 获取设备状态(平台1.0)
 * 
 * @author dengjie
 * @deprecated 仅仅1.0监控平台才支持。目前暂不支持监控平台1.0
 * 
 */
public class GetDeviceStatusNotifyV1 extends CuNotify {

	/**
	 * 命令值
	 */
	public static final String NAME = "getdevicestatus";
	/**
	 * 设备编号
	 */
	private String pustatPuid;
	/**
	 * 设备型号：0：其它，1：审讯主机，2：庭审主机，3：NVR
	 */
	private int pustatModel;
	/**
	 * 设备下面的通道数目
	 */
	private int pustatChnnum;
	/**
	 * Hdmi信息： 数组中元素表示对应的hdmi通道实际启用的数目
	 */
	private int[] pustatHdmi;
	/**
	 * 是否在线
	 */
	private boolean pustatIsonline;
	/**
	 * 磁盘是否已满
	 */
	private boolean pustatIsdiskfull;
	/**
	 * 告警输入1：告警
	 */
	private int[] pustatAlarmin;
	/**
	 * 告警是否启用1：启用
	 */
	private int[] pustatAlarmused;
	/**
	 * 告警输出1：告警
	 */
	private int[] pustatAlarmout;
	/**
	 * 通道号
	 */
	private int chnstatChnid;
	/**
	 * 视频源
	 */
	private int chnstatVideoid;
	/**
	 * 名称
	 */
	private String chnstatName;
	/**
	 * 编码通道状态(二进制位数) 0位:是否在平台预录 1位:是否在平台录像 2位:是否在前端预录 3位:是否在前端录像
	 * 4位:轮训标志1,5位:轮训标志2 （4,5组合含义： 4:0 5:0 无轮训 4: 0,5:1正在轮训 4:1 5:0 暂停 4:1
	 * 5:1停止） 6位:是否启用,7位:是否在线
	 */
	private int chnstatEncstatus;
	/**
	 * 0:无主辅流,1:主2:辅
	 */
	private int chnstatSlaver;
	/**
	 * 正在播放的编码通道（对解码器有用）
	 */
	private int chnstatEnchannelChnid;
	/**
	 * 正在播放的编码通道（对解码器有用）
	 */
	private String chnstatEnchannelPuid;
	/**
	 * 音频输入源（对编码器，音频呼叫有用）
	 */
	private int chnstatAchannelChnid;
	/**
	 * 音频输入源（对编码器，音频呼叫有用）
	 */
	private String chnstatAchannelPuid;
	/**
	 * 在哪个hdmi口(解码器)
	 */
	private int chnstatHdmiindex;
	/**
	 * 是否在移动侦测
	 */
	private boolean chnstatIsvideomove;
	/**
	 * 视频是否丢失
	 */
	private boolean chnstatIsvideolost;
	/**
	 * 是否是智能告警
	 */
	private boolean chnstatIsintealarm;
	/**
	 * 一个递增的序号，方便业务多线程处理
	 */
	private int no;

	@Override
	public void parseData(JSONObject jsonData) throws DataException {
		super.parseNty(jsonData);
		JSONObject pustat = jsonData.optJSONObject("pustat");
		if (pustat != null) {
			JSONArray alarmin = pustat.optJSONArray("alarmin");
			if (alarmin != null && alarmin.length() > 0) {
				this.pustatAlarmin = new int[alarmin.length()];
				for (int i = 0; i < alarmin.length(); i++) {
					pustatAlarmin[i] = alarmin.optInt(i);
				}
			}

			JSONArray alarmout = pustat.optJSONArray("alarmout");
			if (alarmout != null && alarmout.length() > 0) {
				this.pustatAlarmout = new int[alarmout.length()];
				for (int i = 0; i < alarmout.length(); i++) {
					pustatAlarmout[i] = alarmout.optInt(i);
				}
			}
			JSONArray alarmused = pustat.optJSONArray("alarmused");
			if (alarmused != null && alarmused.length() > 0) {
				this.pustatAlarmused = new int[alarmused.length()];
				for (int i = 0; i < alarmused.length(); i++) {
					pustatAlarmused[i] = alarmused.optInt(i);
				}
			}
			JSONArray hdmi = pustat.optJSONArray("hdmi");
			if (hdmi != null && hdmi.length() > 0) {
				this.pustatHdmi = new int[hdmi.length()];
				for (int i = 0; i < hdmi.length(); i++) {
					pustatHdmi[i] = hdmi.optInt(i);
				}
			}
			this.pustatChnnum = pustat.optInt("chnnum");
			this.pustatIsdiskfull = pustat.optBoolean("isdiskfull");
			this.pustatIsonline = pustat.optBoolean("isonline");
			this.pustatModel = pustat.optInt("model");
			this.pustatPuid = pustat.optString("puid");

		}
		JSONArray chnstatArr = jsonData.optJSONArray("chnstat");
		if (chnstatArr != null && chnstatArr.length() > 0) {
			JSONObject chnstat = chnstatArr.optJSONObject(0);
			JSONObject adsrcchannel = chnstat.optJSONObject("adsrcchannel");
			this.chnstatAchannelChnid = adsrcchannel.optInt("chnid");
			this.chnstatAchannelPuid = adsrcchannel.optString("puid");
			this.chnstatChnid = chnstat.optInt("chnid");
			JSONObject enchannel = chnstat.optJSONObject("enchannel");
			this.chnstatEnchannelChnid = enchannel.optInt("chnid");
			this.chnstatEnchannelPuid = enchannel.optString("puid");
			this.chnstatEncstatus = chnstat.optInt("encstatus");
			this.chnstatHdmiindex = chnstat.optInt("hdmiindex");
			this.chnstatIsintealarm = chnstat.optBoolean("isintealarm");
			this.chnstatIsvideolost = chnstat.optBoolean("isvideolost");
			this.chnstatIsvideomove = chnstat.optBoolean("isvideomove");
			this.chnstatName = chnstat.optString("name");
			this.chnstatSlaver = chnstat.optInt("slaver");
			this.chnstatVideoid = chnstat.optInt("videoid");
		}
		this.no = jsonData.optInt("no");
	}

	public String getPustatPuid() {
		return pustatPuid;
	}

	public void setPustatPuid(String pustatPuid) {
		this.pustatPuid = pustatPuid;
	}

	public int getPustatModel() {
		return pustatModel;
	}

	public void setPustatModel(int pustatModel) {
		this.pustatModel = pustatModel;
	}

	public int getPustatChnnum() {
		return pustatChnnum;
	}

	public void setPustatChnnum(int pustatChnnum) {
		this.pustatChnnum = pustatChnnum;
	}

	public int[] getPustatHdmi() {
		return pustatHdmi;
	}

	public void setPustatHdmi(int[] pustatHdmi) {
		this.pustatHdmi = pustatHdmi;
	}

	public boolean isPustatIsonline() {
		return pustatIsonline;
	}

	public void setPustatIsonline(boolean pustatIsonline) {
		this.pustatIsonline = pustatIsonline;
	}

	public boolean isPustatIsdiskfull() {
		return pustatIsdiskfull;
	}

	public void setPustatIsdiskfull(boolean pustatIsdiskfull) {
		this.pustatIsdiskfull = pustatIsdiskfull;
	}

	public int[] getPustatAlarmin() {
		return pustatAlarmin;
	}

	public void setPustatAlarmin(int[] pustatAlarmin) {
		this.pustatAlarmin = pustatAlarmin;
	}

	public int[] getPustatAlarmused() {
		return pustatAlarmused;
	}

	public void setPustatAlarmused(int[] pustatAlarmused) {
		this.pustatAlarmused = pustatAlarmused;
	}

	public int[] getPustatAlarmout() {
		return pustatAlarmout;
	}

	public void setPustatAlarmout(int[] pustatAlarmout) {
		this.pustatAlarmout = pustatAlarmout;
	}

	public int getChnstatChnid() {
		return chnstatChnid;
	}

	public void setChnstatChnid(int chnstatChnid) {
		this.chnstatChnid = chnstatChnid;
	}

	public int getChnstatVideoid() {
		return chnstatVideoid;
	}

	public void setChnstatVideoid(int chnstatVideoid) {
		this.chnstatVideoid = chnstatVideoid;
	}

	public String getChnstatName() {
		return chnstatName;
	}

	public void setChnstatName(String chnstatName) {
		this.chnstatName = chnstatName;
	}

	public int getChnstatEncstatus() {
		return chnstatEncstatus;
	}

	public void setChnstatEncstatus(int chnstatEncstatus) {
		this.chnstatEncstatus = chnstatEncstatus;
	}

	public int getChnstatSlaver() {
		return chnstatSlaver;
	}

	public void setChnstatSlaver(int chnstatSlaver) {
		this.chnstatSlaver = chnstatSlaver;
	}

	public int getChnstatEnchannelChnid() {
		return chnstatEnchannelChnid;
	}

	public void setChnstatEnchannelChnid(int chnstatEnchannelChnid) {
		this.chnstatEnchannelChnid = chnstatEnchannelChnid;
	}

	public String getChnstatEnchannelPuid() {
		return chnstatEnchannelPuid;
	}

	public void setChnstatEnchannelPuid(String chnstatEnchannelPuid) {
		this.chnstatEnchannelPuid = chnstatEnchannelPuid;
	}

	public int getChnstatAchannelChnid() {
		return chnstatAchannelChnid;
	}

	public void setChnstatAchannelChnid(int chnstatAchannelChnid) {
		this.chnstatAchannelChnid = chnstatAchannelChnid;
	}

	public String getChnstatAchannelPuid() {
		return chnstatAchannelPuid;
	}

	public void setChnstatAchannelPuid(String chnstatAchannelPuid) {
		this.chnstatAchannelPuid = chnstatAchannelPuid;
	}

	public int getChnstatHdmiindex() {
		return chnstatHdmiindex;
	}

	public void setChnstatHdmiindex(int chnstatHdmiindex) {
		this.chnstatHdmiindex = chnstatHdmiindex;
	}

	public boolean isChnstatIsvideomove() {
		return chnstatIsvideomove;
	}

	public void setChnstatIsvideomove(boolean chnstatIsvideomove) {
		this.chnstatIsvideomove = chnstatIsvideomove;
	}

	public boolean isChnstatIsvideolost() {
		return chnstatIsvideolost;
	}

	public void setChnstatIsvideolost(boolean chnstatIsvideolost) {
		this.chnstatIsvideolost = chnstatIsvideolost;
	}

	public boolean isChnstatIsintealarm() {
		return chnstatIsintealarm;
	}

	public void setChnstatIsintealarm(boolean chnstatIsintealarm) {
		this.chnstatIsintealarm = chnstatIsintealarm;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public static String getName() {
		return NAME;
	}

}
