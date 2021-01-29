package com.kedacom.middleware.cu.domain;

/**
 * Gps信息
 * 
 * @author dengjie
 * 
 */
public class Gps {
	/**
	 * Gps号
	 */
	private int sn;
	/**
	 * 产生的时间
	 */
	private String time;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;
	/**
	 * 纠偏后的经度
	 */
	private String marLongitude;
	/**
	 * 纠偏后的纬度
	 */
	private String marLatitude;
	/**
	 * 移动设备的速度
	 */
	private String speed;
	
	/**
	 * 设备的puid
	 */
   private String puid;
   
	public int getSn() {
		return sn;
	}

	public void setSn(int sn) {
		this.sn = sn;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getMarLongitude() {
		return marLongitude;
	}

	public void setMarLongitude(String marLongitude) {
		this.marLongitude = marLongitude;
	}

	public String getMarLatitude() {
		return marLatitude;
	}

	public void setMarLatitude(String marLatitude) {
		this.marLatitude = marLatitude;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}

}
