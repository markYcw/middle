package com.kedacom.middleware.mcu.domain;

public class MTParam {
	 
	 /**
	  * 成员类型
		0:没制定成员
		1：会控指定
		2：发言人跟随
		3：主席跟随
		4：轮询视频跟随
		5：语音激励
	  */
	 private int membertype;
	 
	 /**
	 * 终端（{@link membertype} 为 1 时有效}）
	 */
	 private String mtinfo;
	
	 /**
	  * 0：主流1：辅流（目前无效）
	  * 
	  */
	 @Deprecated
     private int videotype;
	
     /**
      * 是否启用（目前无效）
      */
	 @Deprecated
     private boolean used;

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public int getMembertype() {
		return membertype;
	}

	public void setMembertype(int membertype) {
		this.membertype = membertype;
	}

	public int getVideotype() {
		return videotype;
	}

	public void setVideotype(int videotype) {
		this.videotype = videotype;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
