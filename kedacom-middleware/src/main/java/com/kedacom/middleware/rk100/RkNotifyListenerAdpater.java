package com.kedacom.middleware.rk100;

/**
 * @ClassName RkNotifyListenerAdpater
 * @Description RK 事件监听 适配器
 * @Author zlf
 * @Date 2021/6/10 9:44
 */
public class RkNotifyListenerAdpater implements RkNotifyListener {
    /**
     * RK100掉线通知
     *
     * @param rkId RK100标识
     * @param rkIp RK100IP
     */
    @Override
    public void onRKOffLine(String rkId, String rkIp) {

    }
}
