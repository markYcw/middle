package com.kedacom.middleware.cu.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class CuNotifyFactory {

	private CuNotifyFactory(){}
	
	/**
	 * 初始化。注册所有的通知。
	 */
	@SuppressWarnings("deprecation")
	public static void init(){
		
		NotifyFactory.register(DeviceType.CU, DeviceStatusNotify.NAME, DeviceStatusNotify.class);
		NotifyFactory.register(DeviceType.CU, GetGroupNotify.NAME, GetGroupNotify.class);
		NotifyFactory.register(DeviceType.CU, GetDeviceNotify.NAME, GetDeviceNotify.class);
		NotifyFactory.register(DeviceType.CU, GetDeviceStatusNotifyV1.NAME, GetDeviceStatusNotifyV1.class);
		NotifyFactory.register(DeviceType.CU, GetRecordNotify.NAME, GetRecordNotify.class);
		NotifyFactory.register(DeviceType.CU, GetTimeNotify.NAME, GetTimeNotify.class);
		NotifyFactory.register(DeviceType.CU, LostCnntNotify.NAME, LostCnntNotify.class);
		NotifyFactory.register(DeviceType.CU, StartPlayPlatrecNotify.NAME, StartPlayPlatrecNotify.class);
		NotifyFactory.register(DeviceType.CU, TransCmdNotify.NAME, TransCmdNotify.class);
		

		NotifyFactory.register(DeviceType.CU2, DeviceStatusNotify.NAME, DeviceStatusNotify.class);
		NotifyFactory.register(DeviceType.CU2, GetGroupNotify.NAME, GetGroupNotify.class);
		NotifyFactory.register(DeviceType.CU2, GetDeviceNotify.NAME, GetDeviceNotify.class);
		NotifyFactory.register(DeviceType.CU2, GetRecordNotify.NAME, GetRecordNotify.class);
		NotifyFactory.register(DeviceType.CU2, GetTimeNotify.NAME, GetTimeNotify.class);
		NotifyFactory.register(DeviceType.CU2, LostCnntNotify.NAME, LostCnntNotify.class);
		NotifyFactory.register(DeviceType.CU2, StartPlayPlatrecNotify.NAME, StartPlayPlatrecNotify.class);
		NotifyFactory.register(DeviceType.CU2, TransCmdNotify.NAME, TransCmdNotify.class);
		NotifyFactory.register(DeviceType.CU2, GetTvWallNotify.NAME, GetTvWallNotify.class);
	}
}
