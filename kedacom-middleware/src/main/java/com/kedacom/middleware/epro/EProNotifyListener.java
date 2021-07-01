package com.kedacom.middleware.epro;

/**
 * @ClassName EProNotifyListener
 * @Description E10Pro消息监听器
 * @Author ycw
 * @Date 2021/06/30 15:02
 */
public interface EProNotifyListener {

    /**
     * E10Pro掉线通知
     *
     * @param eProId E10Pro标识
     * @param eProIp E10ProIP
     */
    public void onEProOffLine(String eProId, String eProIp);
}
