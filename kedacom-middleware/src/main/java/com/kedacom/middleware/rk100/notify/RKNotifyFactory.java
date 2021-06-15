package com.kedacom.middleware.rk100.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class RKNotifyFactory {

    private RKNotifyFactory() {
    }

    /**
     * 初始化。注册所有的通知。
     */
    public static void init() {
        NotifyFactory.register(DeviceType.RK100, LostCntNotify.NAME, LostCntNotify.class);
    }

}
