package com.kedacom.middleware.mcu.domain;

import java.util.List;

/**
 * 电视墙信息
 * @author root
 * 
 * 2018-02-02 LinChaoYu 适配会议平台5.0及以上版本
 *
 */
public class TVWalls {
	
	/**
	 * 电视墙别名
	 */
	private String alias;
	
	/**
	 * 电视墙ID
	 */
    private String eqpid;
	
    /**
     * apsid
     */
    private int apsid;
	
    /**
     * 是否在线
     */
    private boolean isonline;
    
    /**
     * 是否被占用
     */
    private boolean occupy;
    
    /**
     * 选看模式
     * 1-选看；2-四分屏(仅传统会议有效)；3-单通道轮询；
     */
    private int mode;
    
    /* === 通道选看信息 === */
    
    /**
     * 选看类型
     * 1-指定；2-发言人跟随；3-主席跟随；4-会议轮询跟随；6-选看画面合成；10-选看双流；
     */
    private int specificMembertype;
    
    /**
     * 选看画面合成id，
     * 仅{@link specificMembertype}为 6-选看画面合成 时生效 
     * 最大字符长度：48个字节
     */
    private String specificVmpid;
    
    /* === 轮询信息 === */
    
    /**
     * 轮询次数
     */
    private int pollNum;
    
    /**
     * 轮询方式
     * 1-仅图像；3-音视频轮询；
     */
    private int pollMode;
    
    /**
     * 轮询间隔（秒）
     */
    private int pollKeeptime;
    
    /**
     * 电视墙通道集合
     * 仅在 mode 为2,3 或者 (mode为1 且 specificMembertype为1)时候有效
     */
    private List<Chns> chns;
    

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEqpid() {
		return eqpid;
	}

	public void setEqpid(String eqpid) {
		this.eqpid = eqpid;
	}

	public int getApsid() {
		return apsid;
	}

	public void setApsid(int apsid) {
		this.apsid = apsid;
	}

	public boolean isIsonline() {
		return isonline;
	}

	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}

	public List<Chns> getChns() {
		return chns;
	}

	public void setChns(List<Chns> chns) {
		this.chns = chns;
	}

	public boolean isOccupy() {
		return occupy;
	}

	public void setOccupy(boolean occupy) {
		this.occupy = occupy;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getSpecificMembertype() {
		return specificMembertype;
	}

	public void setSpecificMembertype(int specificMembertype) {
		this.specificMembertype = specificMembertype;
	}

	public String getSpecificVmpid() {
		return specificVmpid;
	}

	public void setSpecificVmpid(String specificVmpid) {
		this.specificVmpid = specificVmpid;
	}

	public int getPollNum() {
		return pollNum;
	}

	public void setPollNum(int pollNum) {
		this.pollNum = pollNum;
	}

	public int getPollMode() {
		return pollMode;
	}

	public void setPollMode(int pollMode) {
		this.pollMode = pollMode;
	}

	public int getPollKeeptime() {
		return pollKeeptime;
	}

	public void setPollKeeptime(int pollKeeptime) {
		this.pollKeeptime = pollKeeptime;
	}
}
