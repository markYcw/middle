package com.kedacom.middleware.mcu.domain;

/**
 * 终端状态
 * @author TaoPeng
 *
 */
public class MTStatus {
	
	/**
	 * 0:offline,1:online,2:idle,3:busy
	 */
	private int status;

    /**
     * 是否静音
     */
	private boolean isquiet;
    
    /**
     * 是否哑音
     */
	private boolean isdumb;
    
    /**
     * 当前视频源
     */
	private int curvideo;
    
    /**
     * 是否在发生第二视频
     */
	private boolean issndvideo2;
    
    /**
     * 输入音量
     */
	private int inputvolume;
    
    /**
     * 输出音量
     */
	private int outputvolume;
    
    /**
     * 是否有矩阵
     */
	private boolean ishasmatrix;
    
    /**
     * 能否遥控摄像头
     */
	private boolean isenablefecc;
    
    /**
     * 是否在混音
     */
	private boolean ismixing;
    
    /**
     * 是否在合成画面中
     */
	private boolean isinvmp;
    
    /**
     * 是否在电视墙中
     */
	private boolean isintvwall;
    
    /**
     * 是否在录像
     */
	private boolean isrec;
    
    /**
     * 是否在传送音频
     */
	private boolean issndaudio;
    
    /**
     * 是否在传送视频 
     */
	private boolean issndvideo;
    
    /**
     * 选看的主视频终端
     */
	private String lookmainmt;
    
    /**
     * 选看的辅视频终端
     */
	private String looksecmt;
    
    /**
     * 选看的音频终端
     */
	private String lookaudmt;
    
    /**
     * 终端信息
     */
	private String mtinfo;
	
	/**
     * 上报序号
     */
	private int ssno;
	
	
	/**
	 * 会议E164
	 */
	private String confe164;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isIsquiet() {
		return isquiet;
	}

	public void setIsquiet(boolean isquiet) {
		this.isquiet = isquiet;
	}

	public boolean isIsdumb() {
		return isdumb;
	}

	public void setIsdumb(boolean isdumb) {
		this.isdumb = isdumb;
	}

	public int getCurvideo() {
		return curvideo;
	}

	public void setCurvideo(int curvideo) {
		this.curvideo = curvideo;
	}

	public boolean isIssndvideo2() {
		return issndvideo2;
	}

	public void setIssndvideo2(boolean issndvideo2) {
		this.issndvideo2 = issndvideo2;
	}

	public int getInputvolume() {
		return inputvolume;
	}

	public void setInputvolume(int inputvolume) {
		this.inputvolume = inputvolume;
	}

	public int getOutputvolume() {
		return outputvolume;
	}

	public void setOutputvolume(int outputvolume) {
		this.outputvolume = outputvolume;
	}

	public boolean isIshasmatrix() {
		return ishasmatrix;
	}

	public void setIshasmatrix(boolean ishasmatrix) {
		this.ishasmatrix = ishasmatrix;
	}

	public boolean isIsenablefecc() {
		return isenablefecc;
	}

	public void setIsenablefecc(boolean isenablefecc) {
		this.isenablefecc = isenablefecc;
	}

	public boolean isIsmixing() {
		return ismixing;
	}

	public void setIsmixing(boolean ismixing) {
		this.ismixing = ismixing;
	}

	public boolean isIsinvmp() {
		return isinvmp;
	}

	public void setIsinvmp(boolean isinvmp) {
		this.isinvmp = isinvmp;
	}

	public boolean isIsintvwall() {
		return isintvwall;
	}

	public void setIsintvwall(boolean isintvwall) {
		this.isintvwall = isintvwall;
	}

	public boolean isIsrec() {
		return isrec;
	}

	public void setIsrec(boolean isrec) {
		this.isrec = isrec;
	}

	public boolean isIssndaudio() {
		return issndaudio;
	}

	public void setIssndaudio(boolean issndaudio) {
		this.issndaudio = issndaudio;
	}

	public boolean isIssndvideo() {
		return issndvideo;
	}

	public void setIssndvideo(boolean issndvideo) {
		this.issndvideo = issndvideo;
	}

	public String getLookmainmt() {
		return lookmainmt;
	}

	public void setLookmainmt(String lookmainmt) {
		this.lookmainmt = lookmainmt;
	}

	public String getLooksecmt() {
		return looksecmt;
	}

	public void setLooksecmt(String looksecmt) {
		this.looksecmt = looksecmt;
	}

	public String getLookaudmt() {
		return lookaudmt;
	}

	public void setLookaudmt(String lookaudmt) {
		this.lookaudmt = lookaudmt;
	}

	public String getMtinfo() {
		return mtinfo;
	}

	public void setMtinfo(String mtinfo) {
		this.mtinfo = mtinfo;
	}

	public String getConfe164() {
		return confe164;
	}

	public void setConfe164(String confe164) {
		this.confe164 = confe164;
	}

	public int getSsno() {
		return ssno;
	}

	public void setSsno(int ssno) {
		this.ssno = ssno;
	}
    
	
}
