package com.kedacom.middleware.mcu.domain;

/**
 * 终端选看
 * @author LinChaoYu
 *
 */
public class Inspection {

	private int mode;
	
	private int srcType;
	
	private String srcMtId;
	
	private String dstMtId;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public int getSrcType() {
		return srcType;
	}

	public void setSrcType(int srcType) {
		this.srcType = srcType;
	}

	public String getSrcMtId() {
		return srcMtId;
	}

	public void setSrcMtId(String srcMtId) {
		this.srcMtId = srcMtId;
	}

	public String getDstMtId() {
		return dstMtId;
	}

	public void setDstMtId(String dstMtId) {
		this.dstMtId = dstMtId;
	}
}
