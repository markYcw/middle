package com.kedacom.middleware.cu.domain;

public class Binddecs {
	
	//电视机序号
	private int tvid;
	
	//电视机画面数
	private int divnum;
//	解码器通道
	private DecChn decchanl;
//	//解码器设备id
//	private String puid;
//	
//	//解码器输出id
//	private int chnid;

	public int getTvid() {
		return tvid;
	}

	public void setTvid(int tvid) {
		this.tvid = tvid;
	}

	public int getDivnum() {
		return divnum;
	}

	public void setDivnum(int divnum) {
		this.divnum = divnum;
	}

	public DecChn getDecchanl() {
		return decchanl;
	}

	public void setDecchanl(DecChn decchanl) {
		this.decchanl = decchanl;
	}

//	public String getPuid() {
//		return puid;
//	}
//
//	public void setPuid(String puid) {
//		this.puid = puid;
//	}

//	public int getChnid() {
//		return chnid;
//	}
//
//	public void setChnid(int chnid) {
//		this.chnid = chnid;
//	}
}
