package com.kedacom.middleware.mcu.domain;

public class Mts {
   
	/**
	 * 终端E164
	 */
    private String e164;
   
    /**
     * 终端码率;
     */
    private int rate;
    
    /**
     * 终端入会后返回的标识（5.0接口）
     */
    private String mtId;
    
    /**
     * 终端IP地址（5.0接口）
     */
    private String ip;
    
    /**
     * 是否已经呼叫（5.0接口）
     */
    private int called; 
    
    /**
     * 是否静音 0-否；1-是；
     */
    private int silence;
    
    /**
     * 是否哑音 0-否；1-是；
     */
    private int mute;
    
    /**
     * 是否发送双流 0-否；1-是；
     */
    private int dual;
    
    /**
     * 是否在混音 0-否；1-是；
     */
    private int mix;
    
    /**
     * 是否在合成 0-否；1-是；
     */
    private int vmp;
    
    /**
     * 是否在选看 0-否；1-是；
     */
    private int inspection;
    
    /**
     * 是否在录像 0-否；1-是；
     */
    private int rec;
    
    /**
     * 是否在轮荀 0-否；1-是；
     */
    private int poll;
    
    /**
     * 是否在上传 0-否；1-是；
     */
    private int upload;
    
    /**
     * 呼叫协议呼叫协议 0-323；1-sip；
     */
    private int protocol;
    
    /**
     * 呼叫模式 0-手动；1-自动；2-定时呼叫；
     */
    private int call_mode;
    
    /**
     * 类型
     */
    private int type;

	/**
	 * 终端别名
	 */
	private String alias;

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getMtId() {
		return mtId;
	}

	public void setMtId(String mtId) {
		this.mtId = mtId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getCalled() {
		return called;
	}

	public void setCalled(int called) {
		this.called = called;
	}

	public int getDual() {
		return dual;
	}

	public void setDual(int dual) {
		this.dual = dual;
	}

	public int getMix() {
		return mix;
	}

	public void setMix(int mix) {
		this.mix = mix;
	}

	public int getVmp() {
		return vmp;
	}

	public void setVmp(int vmp) {
		this.vmp = vmp;
	}

	public int getInspection() {
		return inspection;
	}

	public void setInspection(int inspection) {
		this.inspection = inspection;
	}

	public int getRec() {
		return rec;
	}

	public void setRec(int rec) {
		this.rec = rec;
	}

	public int getPoll() {
		return poll;
	}

	public void setPoll(int poll) {
		this.poll = poll;
	}

	public int getUpload() {
		return upload;
	}

	public void setUpload(int upload) {
		this.upload = upload;
	}

	public int getProtocol() {
		return protocol;
	}

	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}

	public int getCall_mode() {
		return call_mode;
	}

	public void setCall_mode(int call_mode) {
		this.call_mode = call_mode;
	}

	public int getSilence() {
		return silence;
	}

	public void setSilence(int silence) {
		this.silence = silence;
	}

	public int getMute() {
		return mute;
	}

	public void setMute(int mute) {
		this.mute = mute;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}
