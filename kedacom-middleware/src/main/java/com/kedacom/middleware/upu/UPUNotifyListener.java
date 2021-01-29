package com.kedacom.middleware.upu;


/**
 * UPU消息监听器
 * 
 * @author LinChaoYu
 *
 */
public interface UPUNotifyListener{
	
	/**
	 * UPU掉线通知
	 * @param upuId UPU的标识（数据库ID）
	 * @param upuIp UPU的IP
	 */
	public void onUPUOffine(String upuId, String upuIp);
	
}
