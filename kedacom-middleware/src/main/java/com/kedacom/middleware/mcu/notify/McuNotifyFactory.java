package com.kedacom.middleware.mcu.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class McuNotifyFactory {

	private McuNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	public static void init(){
		
		//4.7版本
		NotifyFactory.register(DeviceType.MCU, ConfStatusNotify.NAME, ConfStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.MCU, MTStatusNotify.NAME, MTStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU, VcrStatusNotify.NAME, VcrStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU, ConfListNotify.NAME, ConfListNotify.class);
		
		//5.0及以上版本
		NotifyFactory.register(DeviceType.MCU5, ConfStatusNotify.NAME, ConfStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU5, LostCntNotify.NAME, LostCntNotify.class);
		NotifyFactory.register(DeviceType.MCU5, MTStatusNotify.NAME, MTStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU5, VcrStatusNotify.NAME, VcrStatusNotify.class);
		NotifyFactory.register(DeviceType.MCU5, ConfListNotify.NAME, ConfListNotify.class);
	}
	
}
