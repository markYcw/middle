package com.kedacom.middleware.svr.notify;


import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.client.NotifyFactory;

public class SVRNotifyFactory {

    private SVRNotifyFactory() {
    }

    /**
     * 初始化。注册所有的通知。
     */
    public static void init() {
        NotifyFactory.register(DeviceType.SVR, LostCntNotify.NAME, LostCntNotify.class);
        NotifyFactory.register(DeviceType.SVR, BurnStatusNotify.NAME, BurnStatusNotify.class);
        NotifyFactory.register(DeviceType.SVR, DownloadrecntyNotify.NAME, DownloadrecntyNotify.class);
        NotifyFactory.register(DeviceType.SVR, SearchEncoderAndDecoderNotify.NAME, SearchEncoderAndDecoderNotify.class);
        NotifyFactory.register(DeviceType.SVR, QueryRecNotify.NAME, QueryRecNotify.class);
        NotifyFactory.register(DeviceType.SVR, GetBurnTaskNotify.NAME, GetBurnTaskNotify.class);
        NotifyFactory.register(DeviceType.SVR, CreateBurnNotify.NAME, CreateBurnNotify.class);
        NotifyFactory.register(DeviceType.SVR, DvdStateChangeNotify.NAME, DvdStateChangeNotify.class);

        NotifyFactory.register(DeviceType.SVR_NVRV7, LostCntNotify.NAME, LostCntNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, BurnStatusNotify.NAME, BurnStatusNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, DownloadrecntyNotify.NAME, DownloadrecntyNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, SearchEncoderAndDecoderNotify.NAME, SearchEncoderAndDecoderNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, QueryRecNotify.NAME, QueryRecNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, GetBurnTaskNotify.NAME, GetBurnTaskNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, CreateBurnNotify.NAME, CreateBurnNotify.class);
        NotifyFactory.register(DeviceType.SVR_NVRV7, DvdStateChangeNotify.NAME, DvdStateChangeNotify.class);
    }

}
