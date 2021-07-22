package com.kedacom.middleware.mcu.domain;

/**
 * 终端选看
 * @author LinChaoYu
 *
 */
public class Inspection {
    //选看模式 1-视频 2-音频
	private int mode;
    //选看源类型 1-终端 2-画面合成
	private int srcType;
	//源终端ID 选看源类型为终端时, 值为终端id； 选看源类型为画面合成时, 值为合成id
	private String srcMtId;
	//目的终端ID 选看源类型为画面合成时, 必须为主席终端号
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
