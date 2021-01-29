package com.kedacom.middleware.mcu.domain;

/**
 * 会议平台
 * @author TaoPeng
 *
 */
public class Mcu {
	
	public static final String MCU_VERSION_4 = "4.7";//MCU 4.7版本
	public static final String MCU_VERSION_5 = "5.0";//MCU 5.0及以上版本
	

	private String id; //会议平台本地标识。比如：MT信息在本地数据库的数据ID
	private String ip;//IP地址
	private int port = 80;//端口（默认80）
	private String name; //名称
	private String user; //登录用户名
	private String password; //登录密码
	
	/**
	 * MCU 版本号
	 */
	private String version = MCU_VERSION_4;
	
	/**
	 * 连接会议平台:帐号（5.0及以上版本有效）
	 */
	private String sdkKey;
	
	/**
	 * 连接会议平台:密码（5.0及以上版本有效）
	 */
	private String sdkSecret;
	
	/**
	 * 默认开启会议类型：0-传统、1-端口（5.0及以上版本有效）
	 */
	private int confType;
	
	/**
	 * 【只是存储数据】是否支持消息推送：0-不支持、1-支持
	 */
	private int msgNty;
	
	/**
	 * 【只是存储数据】是否支持平台录像：0-不支持、1-支持
	 */
	private int rec;
	
	/**
	 * 【只是存储数据】是否支持电视墙：0-不支持、1-支持
	 */
	private int tvWall;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSdkKey() {
		return sdkKey;
	}
	public void setSdkKey(String sdkKey) {
		this.sdkKey = sdkKey;
	}
	public String getSdkSecret() {
		return sdkSecret;
	}
	public void setSdkSecret(String sdkSecret) {
		this.sdkSecret = sdkSecret;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public int getConfType() {
		return confType;
	}
	public void setConfType(int confType) {
		this.confType = confType;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getMsgNty() {
		return msgNty;
	}
	public void setMsgNty(int msgNty) {
		this.msgNty = msgNty;
	}
	
	public int getRec() {
		return rec;
	}
	public void setRec(int rec) {
		this.rec = rec;
	}
	
	public int getTvWall() {
		return tvWall;
	}
	public void setTvWall(int tvWall) {
		this.tvWall = tvWall;
	}
	public void update(Mcu mcu){
		this.setId(mcu.getId());//id也会更新
		this.setIp(mcu.getIp());
		this.setPort(mcu.getPort());
		this.setName(mcu.getName());
		this.setUser(mcu.getUser());
		this.setPassword(mcu.getPassword());
		this.setSdkKey(mcu.getSdkKey());
		this.setSdkSecret(mcu.getSdkSecret());
		this.setVersion(mcu.getVersion());
		this.setConfType(mcu.getConfType());
		this.setRec(mcu.getRec());
		this.setTvWall(mcu.getTvWall());
	}
}
