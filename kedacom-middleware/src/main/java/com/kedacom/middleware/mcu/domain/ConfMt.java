package com.kedacom.middleware.mcu.domain;

public class ConfMt {
   
	/**
	 * 终端E164
	 */
    private String e164;
   
    /**
     * 终端码率;
     */
    private int rate;
    
    /**
     * 类型
     */
    private int type;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
