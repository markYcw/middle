package com.kedacom.middleware.mt.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class MTNotifyFactory {

	private MTNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		NotifyFactory.register(DeviceType.MT, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.MT, MTRobbedNotify.NAME, MTRobbedNotify.class);
		NotifyFactory.register(DeviceType.MT, BurnStatusNotify.NAME, BurnStatusNotify.class);
		NotifyFactory.register(DeviceType.MT, StopP2PNotify.NAME, StopP2PNotify.class);		
		
		NotifyFactory.register(DeviceType.MT5, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.MT5, MTRobbedNotify.NAME, MTRobbedNotify.class);
		NotifyFactory.register(DeviceType.MT5, StopP2PNotify.NAME, StopP2PNotify.class);
		
		NotifyFactory.register(DeviceType.CUPBOARD, TransmitInfoNotify.NAME, TransmitInfoNotify.class);
	}
	
}
