package com.kedacom.middleware.gk;


/**
 * GK消息监听器
 * @author LinChaoYu
 *
 */
public interface GKNotifyListener{
	
	/**
	 * GK掉线通知
	 * @param gkId GK标识
	 * @param gkIp GKIP
	 */
	public void onGKOffine(String gkId, String gkIp);
	
}
