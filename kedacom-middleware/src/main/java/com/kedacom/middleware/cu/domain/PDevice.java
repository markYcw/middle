package com.kedacom.middleware.cu.domain;

import keda.common.util.ToolsUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 监控平台上的设备
 * @author TaoPeng
 * @see PGroup
 * @see PDevice
 * @see PChannel
 *
 */
public class PDevice {

	/**设备类型：编码器*/
	public static final int DEVICE_TYPE_ENCODER = 1;
	/**设备类型：解码器*/
	public static final int DEVICE_TYPE_DECODER = 2;
	/**设备类型：电视墙*/
	public static final int DEVICE_TYPE_TVWALL = 4;
	/**设备类型：NVR*/
	public static final int DEVICE_TYPE_NVR = 5;
	/**设备类型：SVR*/
	public static final int DEVICE_TYPE_SVR = 6;
	/**设备类型：报警主机（告警主机）*/
	public static final int DEVICE_TYPE_ALARMHOST = 7;

	/**
	 * 设备所在的平台域的域编号(2.0有效)
	 */
	private String domain;
	/**
	 * 所在的组ID
	 * @see PGroup#getId()
	 */
	private String groupId;
	/**
	 * 设备号
	 */
	private String puid;
	/**
	 * 设备名称
	 */
	private String name;
	
	/**
	 * 设备类型
	 * <pre>
	 * {@link #DEVICE_TYPE_ENCODER} 编码器
	 * {@link #DEVICE_TYPE_DECODER} 解码器
	 * {@link #DEVICE_TYPE_TVWALL} 电视墙
	 * {@link #DEVICE_TYPE_NVR} NVR
	 * {@link #DEVICE_TYPE_SVR} SVR
	 * {@link #DEVICE_TYPE_ALARMHOST} 报警主机（告警主机）
	 * </pre>
	 */
	private int deviceType;
	/**
	 * 权限：（二进制位数） 1：云镜控制(1-10)级
	 * 第一位开始，共占用4位。9：前端录像。10：前端放像。11：前端录像删除。12：前端录像下载。17
	 * ：前端录像设置。18：前端开关量输出控制。19：布防，撤防。
	 */
	private int prilevel;
	/**
	 * 设备厂商
	 */
	private String manufact;
	/**
	 * 视频源编号(2.0有效) 视频源名称(2.0有效)
	 */
	private HashMap<Integer, PChannel> channels = new HashMap<Integer, PChannel>();
	
	/**
	 * 设备是否在线
	 */
	private boolean online;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPuid() {
		return puid;
	}
	public void setPuid(String puid) {
		this.puid = puid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return 返回设备类型
	 * <pre>
	 * {@link #DEVICE_TYPE_ENCODER} 编码器
	 * {@link #DEVICE_TYPE_DECODER} 解码器
	 * {@link #DEVICE_TYPE_TVWALL} 电视墙
	 * {@link #DEVICE_TYPE_NVR} NVR
	 * {@link #DEVICE_TYPE_SVR} SVR
	 * {@link #DEVICE_TYPE_ALARMHOST} 报警主机（告警主机）
	 * </pre>
	 */
	public int getDeviceType() {
		return deviceType;
	}
	
	/**
	 * 设置设备类型
	 * @param deviceType
	 */
	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}
	public int getPrilevel() {
		return prilevel;
	}
	public void setPrilevel(int prilevel) {
		this.prilevel = prilevel;
	}
	public String getManufact() {
		return manufact;
	}
	public void setManufact(String manufact) {
		this.manufact = manufact;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	/**
	 * 获取全部通道
	 * @return
	 */
	public List<PChannel> getChannels() {
		List<PChannel>  list = new ArrayList<PChannel> (this.channels.size());
		list.addAll(this.channels.values());
		return list;
	}
	
	/**
	 * 获取指定通道
	 * @param sn
	 * @return
	 */
	public PChannel getChannel(int sn){
		return this.channels.get(sn);
	}
	/**
	 * 增加通道
	 * @param channel
	 */
	public void addChannel(PChannel channel){
		channel.setPuid(this.getPuid());
		if(ToolsUtil.isEmpty(channel.getName()))
			channel.setName(this.getName());
		
		this.channels.put(channel.getSn(), channel);
	}
	public void addChannels(Collection<PChannel> channels) {
		for(PChannel chl : channels){
			this.addChannel(chl);
		}
	}
	
	/**
	 * 移除通道
	 * @param sn
	 */
	public void removeChannel(int sn){
		this.channels.remove(sn);
	}
	
	/**
	 * 清空通道
	 */
	public void clearChannel(){
		this.channels.clear();
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
}
