package com.kedacom.middleware.svr.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class SVRNotifyFactory {

	private SVRNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		NotifyFactory.register(DeviceType.SVR, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.SVR, BurnStatusNotify.NAME, BurnStatusNotify.class);
		NotifyFactory.register(DeviceType.SVR, DownloadrecntyNotify.NAME, DownloadrecntyNotify.class);
		NotifyFactory.register(DeviceType.SVR, SearchEncoderAndDecoderNotify.NAME, SearchEncoderAndDecoderNotify.class);
		NotifyFactory.register(DeviceType.SVR,QueryRecNotify.NAME, QueryRecNotify.class);
	}
	
}
