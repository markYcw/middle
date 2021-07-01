package com.kedacom.middleware.epro;

/**
 * @ClassName EProNotifyListenerAdpater
 * @Description E10Pro 事件监听 适配器
 * @Author ycw
 * @Date 2021/06/30 15:16
 */
public class EProNotifyListenerAdpater implements EProNotifyListener {
    /**
     * E10Pro掉线通知
     *
     * @param eProId E10Pro标识
     * @param eProIp E10ProIP
     */
    @Override
    public void onEProOffLine(String eProId, String eProIp) {
        System.out.println("========E10Pro掉线通知"+eProIp);
    }
}
