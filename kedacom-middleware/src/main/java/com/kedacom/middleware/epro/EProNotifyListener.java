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

    /**
     * 指纹图片通知
     * @param ip 设备IP
     * @param fingerPrintPath 指纹图片路径
     */
    public void onFingerPrint(String ip, String fingerPrintPath);

    /**
     * 签名图片通知
     * @param ip 设备IP
     * @param signPicPath 签名图片路径
     */
    public void onSingPicture(String ip, String signPicPath);

    /**
     * 签名文件通知
     * @param ip 设备IP
     * @param signPdf 签名文件路径
     */
    public void onSignPdf(String ip, String signPdf);

    /**
     * 录像文件通知
     * @param ip 设备IP
     * @param recordPath 签名文件路径
     */
    public void onRecord(String ip, String recordPath);

}
