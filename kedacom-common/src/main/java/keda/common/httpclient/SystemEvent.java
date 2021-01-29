package keda.common.httpclient;

import java.io.Serializable;

/**
 * 业务系统产生事件实体
 */
public class SystemEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	/**设备状态*/
	public static final int TYPE_DEVICESTATUS = 1;
	/**电视墙*/
	public static final int TYPE_TVWALL = 2;
	/**平台发生断连*/
	public static final int TYPE_PLATFORMRECONNECT = 3;
	/**增加图层*/
	public static final int TYPE_MAPLAYERADD = 4;
	/**修改图层*/
	public static final int TYPE_MAPLAYERUPD = 5;
	/**删除图层*/
	public static final int TYPE_MAPLAYERDEL = 6;
	/**增加图元*/
	public static final int TYPE_MAPSHAPEADD = 7;
	/**修改图元*/
	public static final int TYPE_MAPSHAPEUPD = 8;
	/**删除图元*/
	public static final int TYPE_MAPSHAPEDEL = 9;
	/**电子地图暂时未用*/
	public static final int TYPE_MAPLAYERCIMG = 10;
	/**图层更换背景图片*/
	public static final int TYPE_MAPIMAGECHG = 11;
	
	/**创建多方会审*/
	public static final int TYPE_MEETINGCREATED = 12;
	/**加入多方会审*/
	public static final int TYPE_MEETINGJOINED = 13;
	/**退出多方会审*/
	public static final int TYPE_MEETINGEXITED = 14;
	/**关闭多方会审*/
	public static final int TYPE_MEETINGCLOSED = 15;
	/**添加与会成员*/
	public static final int TYPE_MEETINGADDMEMBER = 26;
	
	/**用户链发生断链*/
	public static final int TYPE_LOSTCONNECT = 16;
	/**用户登出*/
	public static final int TYPE_LOGINOUT = 17;
	/**与系统断连*/
	public static final int TYPE_LOSTSYSCONNECT = 18;
	/**录像播放进度上报*/
	public static final int TYPE_PLATPLAYPROGRESS=19;
	/**设备上下线*/
	public static final int TYPE_DEVICEONLINE = 20;
	/**单点登录*/
	public static final int TYPE_ISLOGIN = 21;
	/**重新登录*/
	public static final int TYPE_RELOGIN = 22;
	/**本级-级联注册成功*/
	public static final int TYPE_REGIST_SUCCESS = 23;
	/**本级-级联注册失败**/
	public static final int TYPE_REGIST_FAIL = 1023;
	/**下级-上报平台**/
	public static final int TYPE_LOWER_UPLOAD_PLATFORM = 24;
	/**本级-下级级联成功*/
	public static final int TYPE_LOWER_REGIST = 27;
	/**笔录附件删除事件 */
	public static final int TYPE_DELETE_ATTACHMENT = 28;
	/**创建远程提审*/
	public static final int TYPE_REMOTEMEETINGCREATED = 29;
	/**加入远程提审*/
	public static final int TYPE_REMOTEMEETINGJOINED = 30;
	/**退出远程提审*/
	public static final int TYPE_REMOTEMEETINGEXITED = 31;
	/**关闭远程提审*/
	public static final int TYPE_REMOTEMEETINGCLOSED = 32;
	/**添加远程提审与会成员*/
	public static final int TYPE_REMOTEMEETINGADDMEMBER = 33;
	/**案卷归档事件上报*/
	public static final int TYPE_CASEARCHIVE = 34;
	/**平台连接状态*/
	public static final int TYPE_PLATFORMSTATUS = 35;
	/**审讯室信息发生改变时事件上报*/
	public static final int TYPE_ROOMINFO = 36;
	/**结束远程提审时事件上报*/
	public static final int TYPE_SAVEREMOTERECORD = 37;
	/**开始远程提审时事件上报*/
	public static final int TYPE_STARTREMOTERECORD = 43;
	/**平台设备加载成功*/
	public static final int TYPE_DEVICE_LOAD_COMPLETED = 44;
	/**案件附件事件 */
	public static final int TYPE_CASE_ATTACHMENT = 45;
	/**导出日志状态上报*/
	public static final int TYPE_EXPORTLOG = 38;
	/**平台磁盘告警*/
	public static final int TYPE_PLATFORM_ALARM = 39;
	/**平台磁盘正常*/
	public static final int TYPE_PLATFORM_NORMAL = 40;
	/**开始笔录签名*/
	public static final int TYPE_WORD_RECORD_SIGN = 41;
	/**笔录归档事件*/
	public static final int TYPE_WORD_RECORD_ARVHIVE = 42;
	/**word笔录文档未找到事件*/
	public static final int TYPE_RECORD_DOC_NOT_FOUNT= 43;
	/**平台录像被删除通知*/
	public static final int TYPE_DELETE_VIDEO = 56;
	/**车辆上报**/
//	public static final int TYPE_VIHICLE = 57;
	
	/**车辆告警事件**/
//	public static final int TYPE_ALARMEVENT = 58;
	/**删除笔录信息*/
	public static final int TYPE_DELETE_RECORD = 59;
	/**删除案卷信息*/
	public static final int TYPE_DELETE_CASE = 60;
	
	/** 审讯时间提醒*/
	public static final int TYPE_TRIALTIMENTY = 94;
	/**重连平台消息通知*/
	public static final int TYPE_RECONNECTPLATFORM = 95;
	/**刻录机刻录状态*/
	public static final int TYPE_BURNSTATUS = 96;
	/**改变自定义配置时*/
	public static final int TYPE_CHANGESETUP = 97;
	/**获取光盘状态*/
	public static final int TYPE_DVDSTATE = 98;
	/**
	 * 级联状态事件
	 */
	public static final int TYPE_SYNCHRONIZATION = 99;
	
	/**聊天邀请事件*/
	public static final int TYPE_CHATINVITE = 100;
	
	/**发起音频通知事件*/
	public static final int TYPE_AUDIOCALL = 998;
	
	/**重新登录事件*/
	public static final int TYPE_DISPOSEMSG = 999;
	
	/**设备GPS信息通知*/
	public static final int TYPE_DEVICE_GPS = 1000;
	
	/**
	 * 远程提审SVR上线事件
	 */
	public static final int TYPE_REMOTETRIAL_ONLINE = 100;
	/**修改涉案人员照片*/
	public static final int TYPE_UPDATE_PERSON_PHOTO = 46;
	public static final int SAMBA_LINE_OFF= 47;
	/**磁盘容量低*/
	public static final int DISK_LOWER= 48;
	public static final int DISK_INSUF= 49;
	public static final int RECORD_SAMBA_LINE_OFF= 50;
	public static final int RECORD_DISK_LOWER= 51;
	public static final int RECORD_DISK_INSUF= 52;
	public static final int MAP_SAMBA_LINE_OFF= 53;
	public static final int MAP_DISK_LOWER= 54;
	public static final int MAP_DISK_INSUF= 55;
	
	/**以下是PRM的通信事件通知*/
	/**集中刻录进度上报*/
	public static final int CENTRALIZED_BURNING_PROGRESS= 101;
	/**集中刻录发生错误*/
	public static final int CENTRALIZED_BURNING_FAILE= 102;
	/**集中刻录完成*/
	public static final int CENTRALIZED_BURNING_COMPLETE= 103;
	/**与PRM连接成功*/
	public static final int PRM_CONNECT_SUCCESS= 104;
	/**与PRM连接失败*/
	public static final int PRM_CONNECT_FAILE= 105;
	/**打印机状态*/
	public static final int PRINTER_STATUS=106;
	/** 实时刻录进度上报*/
	public static final int LIVE_BURN_PROGRESS=107;
	
	/**
	 * @author zhangyang
	 */
	/** 事后刻录进度上报*/
	public static final int REC_BURN_PROGRESS=108;
	/** 事后刻录完成*/
	public static final int REC_BURN_SUCCESS=109;
	/** 事后刻录失败*/
	public static final int REC_BURN_FAILE=110;
	
	
	/**
	 * 事件id
	 */
	private long eventId;
	/**
	 * 事件类型
	 */
	private int eventType;
	/**
	 * 事件传递数据
	 */
	private Object data;
	/**
	 * 发起事件端sessionId
	 */
	private String srcSessionId;
	/**
	 * 目标事件端Sesssionid
	 */
	private String targetSessionId;
	
	public String getSrcSessionId() {
		return srcSessionId;
	}
	public void setSrcSessionId(String srcSessionId) {
		this.srcSessionId = srcSessionId;
	}
	public String getTargetSessionId() {
		return targetSessionId;
	}
	public void setTargetSessionId(String targetSessionId) {
		this.targetSessionId = targetSessionId;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
