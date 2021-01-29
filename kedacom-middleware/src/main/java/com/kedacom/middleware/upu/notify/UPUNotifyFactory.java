package com.kedacom.middleware.upu.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class UPUNotifyFactory {

	private UPUNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		NotifyFactory.register(DeviceType.UPU, LostCntNotify.NAME, LostCntNotify.class);
	}
	
}
