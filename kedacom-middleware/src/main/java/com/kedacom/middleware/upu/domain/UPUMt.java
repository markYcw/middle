package com.kedacom.middleware.upu.domain;



/**
 * 终端参数信息
 * 
 * @author LinChaoYu
 *
 */
public class UPUMt {

	private String e164;
	
	private int status;

	public String getE164() {
		return e164;
	}

	public void setE164(String e164) {
		this.e164 = e164;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
