package com.kedacom.middleware.mt.domain;

//用于查询录像机状态
public class Streaminfo {

	private int recfrmnum;
	
	private int lostfrmnum;

	public Streaminfo() {
	}

	public Streaminfo(int recfrmnum, int lostfrmnum) {
		this.recfrmnum = recfrmnum;
		this.lostfrmnum = lostfrmnum;
	}

	public int getRecfrmnum() {
		return recfrmnum;
	}

	public void setRecfrmnum(int recfrmnum) {
		this.recfrmnum = recfrmnum;
	}

	public int getLostfrmnum() {
		return lostfrmnum;
	}

	public void setLostfrmnum(int lostfrmnum) {
		this.lostfrmnum = lostfrmnum;
	}
	
	
}
