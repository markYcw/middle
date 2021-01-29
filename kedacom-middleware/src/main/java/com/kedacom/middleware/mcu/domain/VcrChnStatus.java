package com.kedacom.middleware.mcu.domain;

/**
 * 录像机通道状态
 * @author TaoPeng
 *
 */
public class VcrChnStatus {

	private int type;
	private int stat; //状态
	private int recMode;//录像方式 0抽帧  1正常
	private long curProg;//进度（单位s)
	private long totalTime;//长度（放像有效）
	private String mtinfo;//终端信息
	private String confe164; //会议信息
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStat() {
		return stat;
	}
	public void setStat(int stat) {
		this.stat = stat;
	}
	public int getRecMode() {
		return recMode;
	}
	public void setRecMode(int recMode) {
		this.recMode = recMode;
	}
	public long getCurProg() {
		return curProg;
	}
	public void setCurProg(long curProg) {
		this.curProg = curProg;
	}
	public long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	public String getMtinfo() {
		return mtinfo;
	}
	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}
	public String getConfe164() {
		return confe164;
	}
	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}
	
	
}
