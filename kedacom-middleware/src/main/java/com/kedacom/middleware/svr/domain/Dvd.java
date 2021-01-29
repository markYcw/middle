package com.kedacom.middleware.svr.domain;

/**
 * SVR的dvd信息
 * 
 * @author DengJie
 * 
 */
public class Dvd {
	/**
	 * 0:无效 1：没有光盘 2：仓门打开 3：准备就绪 4：没有准备就绪
	 */
	public int doorstatus;
	/**
	 * 0：无效 1:空白盘 2：可写 3：已经封盘 4：其他
	 */
	public int discstatus;
	/**
	 * 碟片总容量 单位M
	 */
	public int totalcapacity;
	/**
	 * 碟片空余容量 单位M
	 */
	public int remaincapacity;

	/**
	 * 0:无效 1：空闲 2：刻录 3：检查 4：读
	 */
	public int workstate;

	/**
	 * 工作子状态
	 */
	public int worksubstate;

	/**
	 * 刻录过程剩余时间 单位秒
	 */
	public int remaintime;

	public int getDoorstatus() {
		return doorstatus;
	}

	public void setDoorstatus(int doorstatus) {
		this.doorstatus = doorstatus;
	}

	public int getDiscstatus() {
		return discstatus;
	}

	public void setDiscstatus(int discstatus) {
		this.discstatus = discstatus;
	}

	public int getTotalcapacity() {
		return totalcapacity;
	}

	public void setTotalcapacity(int totalcapacity) {
		this.totalcapacity = totalcapacity;
	}

	public int getRemaincapacity() {
		return remaincapacity;
	}

	public void setRemaincapacity(int remaincapacity) {
		this.remaincapacity = remaincapacity;
	}

	public int getWorkstate() {
		return workstate;
	}

	public void setWorkstate(int workstate) {
		this.workstate = workstate;
	}

	public int getWorksubstate() {
		return worksubstate;
	}

	public void setWorksubstate(int worksubstate) {
		this.worksubstate = worksubstate;
	}

	public int getRemaintime() {
		return remaintime;
	}

	public void setRemaintime(int remaintime) {
		this.remaintime = remaintime;
	}

}
