package com.kedacom.middleware.mcu.domain;

/**
 * 电视墙通道信息
 * @author root
 *
 */
public class Chns {
	/**
	 * 通道对应的mt
	 */
	private String mt;
	
	/**
	 * 会议e164号
	 */
    private String conf;
    
    /**
     * 状态
     */
    private int status;
    
    /**
     * 通道号：四分屏通道号, 从0开始到3
     * {@link TVWalls#getMode()} 为 2 时有效
     */
    private int chnidx;
    
	public String getMt() {
		return mt;
	}
	
	public void setMt(String mt) {
		this.mt = mt;
	}
	
	public String getConf() {
		return conf;
	}
	
	public void setConf(String conf) {
		this.conf = conf;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getChnidx() {
		return chnidx;
	}
	
	public void setChnidx(int chnidx) {
		this.chnidx = chnidx;
	}
    
}
