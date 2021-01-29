package com.kedacom.middleware.vrs;


/**
 * 录播服务器 消息监听器
 * @author LinChaoYu
 *
 */
public interface VRSNotifyListener{
	
	/**
	 * VRS掉线通知
	 * @param vrsId VRS标识
	 * @param vrsIp VRSIP
	 */
	public void onVRSOffine(String vrsId, String vrsIp);
	
}
