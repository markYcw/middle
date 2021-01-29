package com.kedacom.middleware.cu.domain;

/**
 * 录像
 * 
 * @author dengjie
 * 
 */
public class Record {
	/**
	 * 录像ID
	 */
	private String id;
	/**
	 * 域信息
	 */
	private String mcu;
	/**
	 * 开始时间
	 */
	private String starttime;
	/**
	 * 结束时间
	 */
	private String endtime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMcu() {
		return mcu;
	}

	public void setMcu(String mcu) {
		this.mcu = mcu;
	}

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
