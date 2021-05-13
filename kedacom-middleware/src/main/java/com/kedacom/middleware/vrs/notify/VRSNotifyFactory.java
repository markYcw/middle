package com.kedacom.middleware.vrs.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class VRSNotifyFactory {

	private VRSNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		
		//5.0版本
		NotifyFactory.register(DeviceType.VRS50, LostCntNotify.NAME, LostCntNotify.class);
		
		//5.1版本
		NotifyFactory.register(DeviceType.VRS51, LostCntNotify.NAME, LostCntNotify.class);

		//VRS2000B版本
		NotifyFactory.register(DeviceType.VRS2000B, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.VRS2000B, RecPlayNotify.NAME, RecPlayNotify.class);
	}
	
}
