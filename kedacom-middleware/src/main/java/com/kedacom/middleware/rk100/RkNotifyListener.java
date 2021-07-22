package com.kedacom.middleware.rk100;

/**
 * @ClassName RkNotifyListener
 * @Description RK100消息监听器
 * @Author zlf
 * @Date 2021/6/10 9:44
 */
public interface RkNotifyListener {

    /**
     * RK100掉线通知
     *
     * @param rkId RK100标识
     * @param rkIp RK100IP
     */
    public void onRKOffLine(String rkId, String rkIp);
}
