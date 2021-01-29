package com.kedacom.middleware.svr.domain;

/**
 * svr状态
 * @author DengJie
 *
 */
public class SvrState {
	/**
	 * 0：不在线 非0：在线
	 */
	private int online;
	/**
	 * 0：不在线 非0：在线
	 */
	private int burntaskid;
	/**
	 * 0：没有录像1：录像
	 */
	private int recing;
	/**
	 * 0：没有在刻录中1：刻录中
	 */
	private int burning;
	/**
	 * 0：没有刻录任务1：有刻录任务
	 */
	private int burntaskdoing;
	/**
	 * svr光盘1
	 */
	private Dvd dvd1;
	/**
	 * svr光盘2
	 */
	private Dvd dvd2;
	public int getOnline() {
		return online;
	}
	public void setOnline(int online) {
		this.online = online;
	}
	public int getBurntaskid() {
		return burntaskid;
	}
	public void setBurntaskid(int burntaskid) {
		this.burntaskid = burntaskid;
	}
	public int getRecing() {
		return recing;
	}
	public void setRecing(int recing) {
		this.recing = recing;
	}
	public int getBurning() {
		return burning;
	}
	public void setBurning(int burning) {
		this.burning = burning;
	}
	public int getBurntaskdoing() {
		return burntaskdoing;
	}
	public void setBurntaskdoing(int burntaskdoing) {
		this.burntaskdoing = burntaskdoing;
	}
	public Dvd getDvd1() {
		return dvd1;
	}
	public void setDvd1(Dvd dvd1) {
		this.dvd1 = dvd1;
	}
	public Dvd getDvd2() {
		return dvd2;
	}
	public void setDvd2(Dvd dvd2) {
		this.dvd2 = dvd2;
	}
	
	
}
