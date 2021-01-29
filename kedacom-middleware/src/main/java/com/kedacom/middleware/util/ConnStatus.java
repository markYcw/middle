package com.kedacom.middleware.util;

/**
 * 描述连接状态。主要用于外接系统，记录每个模块的运行状态。
 * @author TaoPeng
 *
 */
public class ConnStatus {

	/**
	 * 服务器连接状态：初始状态
	 */
	public static final int STATUS_INIT = 0;
	/**
	 * 服务器连接状态：连接失败
	 */
	public static final int STATUS_FAILED = -1;
	
	/**
	 * 服务器连接状态：连接正常
	 */
	public static final int STATUS_SUCCESS = 1;
	

	/**
	 * 服务器连接状态
	 */
	private int status = STATUS_INIT;
	/**
	 * 连接服务器的最后时间
	 */
	private long lastTime = 0l;
	/**
	 * 服务器异常消息
	 */
	private String error;
	
	/**
	 * 初始化
	 */
	public void init(){
		this.status = STATUS_INIT;
		this.error = null;
		this.lastTime = 0l;
	}
	
	/**
	 * 标记连接成功
	 */
	public void markSuccess(){
		this.status = STATUS_SUCCESS;
		this.error = null;
		this.lastTime = System.currentTimeMillis();
	}
	
	/**
	 * 标记连接失败
	 * @param status
	 */
	public void markFail(String error){
		this.markFail0(STATUS_FAILED, error);
	}
	/**
	 * 标记连接失败
	 * @param status
	 * @param e
	 */
	public void markFail(Exception e){
		String error = convertThrowableToString(e);
		this.markFail0(STATUS_FAILED, error);
	}

	/**
	 * 将Exception转换为字符串
	 * @param cause
	 * @return
	 */
	private static String convertThrowableToString(Throwable cause){
		StringBuffer sb = new StringBuffer(50);
		sb.append(cause.toString());
		for(StackTraceElement trace : cause.getStackTrace()){
			sb.append("\n         at ");
			sb.append(trace.toString());
		}
		
		Throwable p = cause.getCause();
		if(p != null){
			String str = convertThrowableToString(p);
			sb.append("\n Case By ");
			sb.append(p.getMessage());
			sb.append("\n         at ");
			sb.append(str);
		}
		
		return sb.toString();
		
	}
	private void markFail0(int status, String error){
		this.status = status;
		this.error = error;
		this.lastTime = System.currentTimeMillis();
	}
	
	/**
	 * 返回是否连接正常
	 * @return
	 */
	public boolean isSuccess(){
		return status == STATUS_SUCCESS;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * 返回时间值
	 * @return
	 */
	public long getLastTime() {
		return lastTime;
	}
	
	public String getError() {
		return error;
	}
	
	
}
