package com.kedacom.middleware.cu.domain;

/**
 * 时间段
 * 
 * @author dengjie
 * 
 */
public class Times {
	/**
	 * s开始时间
	 */
	private String starttime;
	/**
	 * e结束时间
	 */
	private String endtime;

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

}
