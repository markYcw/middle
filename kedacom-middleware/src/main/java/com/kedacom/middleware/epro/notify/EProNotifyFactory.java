package com.kedacom.middleware.epro.notify;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class EProNotifyFactory {

    private EProNotifyFactory() {
    }

    /**
     * 初始化。注册所有的通知。
     */
    public static void init() {
        NotifyFactory.register(DeviceType.E10PRO, LostCntNotify.NAME, LostCntNotify.class);
        NotifyFactory.register(DeviceType.E10PRO, FingerPrintNotify.NAME, FingerPrintNotify.class);
        NotifyFactory.register(DeviceType.E10PRO, SignPictureNotify.NAME, SignPictureNotify.class);
        NotifyFactory.register(DeviceType.E10PRO, SignPdfNotify.NAME, SignPdfNotify.class);
        NotifyFactory.register(DeviceType.E10PRO, RecordNotify.NAME, RecordNotify.class);
    }

}
