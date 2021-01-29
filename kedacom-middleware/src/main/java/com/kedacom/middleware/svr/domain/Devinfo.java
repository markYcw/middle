package com.kedacom.middleware.svr.domain; 
/**
 *
 * 项目名称：kedacom-middleware
 * 类名称：Devinfo
 * 类描述：编码器和解码器设备信息
 * 创建人：lzs
 * 创建时间：2019-8-7 下午2:43:28
 */
public class Devinfo {
	
	private int dvetype; //设备类型，默认编码器
	
	private int ipcprotetype; //协议
	
	private int manuid; //厂商ID    0：kedacom，1：外厂商 目前无用
	
	private String devipaddr;//设备IP地址，网络序
	
	private int decaddtype;//设备添加方式，默认0--搜索添加；1--主动添加
	
	private int streamtransmode;//rtsp流传输方式，默认0：TCP, 1: UDP 只有在dev_type_name为"RTSP"时有效
	
	private String szguid;//设备GUID，唯一标识设备
	
	private String szname;//设备别名,比如SVR等
	
	private String sztypename;//字符串，设备类型名 比如IPC412等
	
	private String szdevuuid;//Onvif设备uuid
	
	private String szdevxaddr;//Onvif设备xaddr
	
	private String szdevusername;//Onvif设备登陆用户名
	
	private String szdevuserpwd;//Onvif设备登陆密码
	
	private String szdevrtspurl;//URL地址,只有在dev_type_name为"RTSP"时有效

	public int getIpcprotetype() {
		return ipcprotetype;
	}

	public void setIpcprotetype(int ipcprotetype) {
		this.ipcprotetype = ipcprotetype;
	}

	public int getManuid() {
		return manuid;
	}

	public void setManuid(int manuid) {
		this.manuid = manuid;
	}

	public String getDevipaddr() {
		return devipaddr;
	}

	public void setDevipaddr(String devipaddr) {
		this.devipaddr = devipaddr;
	}

	public int getStreamtransmode() {
		return streamtransmode;
	}

	public void setStreamtransmode(int streamtransmode) {
		this.streamtransmode = streamtransmode;
	}

	public String getSzguid() {
		return szguid;
	}

	public void setSzguid(String szguid) {
		this.szguid = szguid;
	}

	public String getSzname() {
		return szname;
	}

	public void setSzname(String szname) {
		this.szname = szname;
	}

	public String getSztypename() {
		return sztypename;
	}

	public void setSztypename(String sztypename) {
		this.sztypename = sztypename;
	}

	public String getSzdevuuid() {
		return szdevuuid;
	}

	public void setSzdevuuid(String szdevuuid) {
		this.szdevuuid = szdevuuid;
	}

	public String getSzdevxaddr() {
		return szdevxaddr;
	}

	public void setSzdevxaddr(String szdevxaddr) {
		this.szdevxaddr = szdevxaddr;
	}

	public String getSzdevusername() {
		return szdevusername;
	}

	public void setSzdevusername(String szdevusername) {
		this.szdevusername = szdevusername;
	}

	public String getSzdevuserpwd() {
		return szdevuserpwd;
	}

	public void setSzdevuserpwd(String szdevuserpwd) {
		this.szdevuserpwd = szdevuserpwd;
	}

	public String getSzdevrtspurl() {
		return szdevrtspurl;
	}

	public void setSzdevrtspurl(String szdevrtspurl) {
		this.szdevrtspurl = szdevrtspurl;
	}

	public int getDvetype() {
		return dvetype;
	}

	public void setDvetype(int dvetype) {
		this.dvetype = dvetype;
	}

	public int getDecaddtype() {
		return decaddtype;
	}

	public void setDecaddtype(int decaddtype) {
		this.decaddtype = decaddtype;
	}

}
