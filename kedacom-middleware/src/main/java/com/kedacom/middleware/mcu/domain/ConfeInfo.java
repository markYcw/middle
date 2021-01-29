package com.kedacom.middleware.mcu.domain;

import java.util.Set;

public class ConfeInfo {
	
    /**
     * 会议举行方式
     * 0:预约,1: 即时2:会议模板3:虚拟即时会议，4:虚拟模板
     */
	private int takemode;
	
	/**
	 * 0未注册gk,1注册
	 */
	private int regtogk;
	
	/**
	 * 会议锁定方式：
     * 0-不锁定1-根据密码操作2-某个会议控制台
	 */
	private int  lockmode;
	
	/**
	 * 录像方式，0-不录，1-录像2-暂停录像
	 */
	private int recmode;
	
	/**
	 * 会议名称
	 */
	private String confname;
	
	/**
	 * 会议e164
	 */
	private String confe164;
	
	/**
	 * 会议id
	 */
	private int confid;
	
	/**
	 * 加密模式：0-不加密1-des加密2-aes加密
	 */
	private int encryptmode;
	
	/**
	 * 
	 */
    private CapSupport capsupport; //同创建时的capsupport
    
    /**
     * 比特率
     */
	private String bitrate;
	
	/**
	 * 开始时间
	 */
	private String starttime;
	
	/**
	 * 持续时间
	 */
	private String duration;
	
	/**
	 * 发言终端
	 */
	private String speekermt;
	
	/**
	 * 主席
	 */
	private String chairmt;
	
	/**
	 * 受邀终端列表
	 */
	private Set<String> mtinfos;
	
	/**
	 * 与会终端列表，
     * 里面内容是标识会议的E164号或者IP
	 */
	private Set<String> joinedmtinfos;

	public int getTakemode() {
		return takemode;
	}

	public void setTakemode(int takemode) {
		this.takemode = takemode;
	}

	public int getRegtogk() {
		return regtogk;
	}

	public void setRegtogk(int regtogk) {
		this.regtogk = regtogk;
	}

	public int getLockmode() {
		return lockmode;
	}

	public void setLockmode(int lockmode) {
		this.lockmode = lockmode;
	}

	public int getRecmode() {
		return recmode;
	}

	public void setRecmode(int recmode) {
		this.recmode = recmode;
	}

	public String getConfname() {
		return confname;
	}

	public void setConfname(String confname) {
		this.confname = confname;
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public int getConfid() {
		return confid;
	}

	public void setConfid(int confid) {
		this.confid = confid;
	}

	public int getEncryptmode() {
		return encryptmode;
	}

	public void setEncryptmode(int encryptmode) {
		this.encryptmode = encryptmode;
	}

	public CapSupport getCapsupport() {
		return capsupport;
	}

	public void setCapsupport(CapSupport capsupport) {
		this.capsupport = capsupport;
	}

	public String getBitrate() {
		return bitrate;
	}

	public void setBitrate(String bitrate) {
		this.bitrate = bitrate;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSpeekermt() {
		return speekermt;
	}

	public void setSpeekermt(String speekermt) {
		this.speekermt = speekermt;
	}

	public String getChairmt() {
		return chairmt;
	}

	public void setChairmt(String chairmt) {
		this.chairmt = chairmt;
	}

	public Set<String> getMtinfos() {
		return mtinfos;
	}

	public void setMtinfos(Set<String> mtinfos) {
		this.mtinfos = mtinfos;
	}

	public Set<String> getJoinedmtinfos() {
		return joinedmtinfos;
	}

	public void setJoinedmtinfos(Set<String> joinedmtinfos) {
		this.joinedmtinfos = joinedmtinfos;
	}

}
