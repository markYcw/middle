package com.kedacom.middleware.gk.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class GKNotifyFactory {

	private GKNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		NotifyFactory.register(DeviceType.GK, LostCntNotify.NAME, LostCntNotify.class);
	}
	
}
