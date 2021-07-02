package com.kedacom.middleware.epro;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.domain.EPro;
import com.kedacom.middleware.epro.notify.*;
import org.apache.log4j.Logger;

/**
 * @ClassName EProClientListener
 * @Description 会话E10Pro事件监听器
 * @Author ycw
 * @Date 2021/06/30 14:11
 */
public class EProClientListener extends TCPClientListenerAdapter {

    private static final Logger log = Logger.getLogger(EProClientListener.class);

    private EProClient client;

    public EProClientListener(EProClient client) {
        this.client = client;
    }

    /**
     * 收到“通知”
     *
     * @param notify
     */
    @Override
    public void onNotify(INotify notify) {
        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().getSessionBySsid(ssid);
        String ip = null;
        if (session != null && session.getEPro() != null) {
            ip = session.getEPro().getIp();
        } else {
            log.warn("无效的会话,ssid=" + ssid);
            return;
        }

        if (ip == null) {
            log.warn("会话中无有效的EPro信息,ssid=" + ssid);
            return;
        }
        if (notify instanceof LostCntNotify) {
            // EPro掉线
            this.onEProOffline((LostCntNotify) notify);
        }else if(notify instanceof FingerPrintNotify){
            //指纹图片通知
            this.onFingerPrint((FingerPrintNotify) notify);
        }else if(notify instanceof SignPictureNotify){
            //签名图片通知
            this.onSingPicture((SignPictureNotify) notify);
        }else if (notify instanceof SignPdfNotify){
            this.onSignPdf((SignPdfNotify) notify);
        }else if (notify instanceof RecordNotify){
            //录像文件通知
            this.onRecord((RecordNotify) notify);
        }
    }

    /**
     * @Description EPro录像文件通知
     * @param:
     * @return:
     * @author: ycw
     * @date:
     */
    private void onRecord(RecordNotify notify){
        log.info("======>EPro录像文件通知(RecordNotify) ssid=" + notify.getSsid() + "，ssno=" + notify.getSsno());

        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().getSessionBySsid(ssid);
        String path = notify.recordPath;
        if (session != null) {
            EPro ePro = session.getEPro();
            if (ePro != null) {
                for (EProNotifyListener l : client.getAllListeners()) {
                    l.onSignPdf(ePro.getIp(), path);
                }
            }
        }
    }

    /**
     * @Description EPro签名文件通知
     * @param:
     * @return:
     * @author: ycw
     * @date:
     */
    private void onSignPdf(SignPdfNotify notify){
        log.info("======>EPro签名文件通知(SignPdfNotify) ssid=" + notify.getSsid() + "，ssno=" + notify.getSsno());

        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().getSessionBySsid(ssid);
        String path = notify.signPdf;
        if (session != null) {
            EPro ePro = session.getEPro();
            if (ePro != null) {
                for (EProNotifyListener l : client.getAllListeners()) {
                    l.onSignPdf(ePro.getIp(), path);
                }
            }
        }
    }

    /**
     * @Description EPro签名图片通知
     * @param:
     * @return:
     * @author: ycw
     * @date:
     */
    private void onSingPicture(SignPictureNotify notify){
        log.info("======>EPro签名图片通知(SignPictureNotify) ssid=" + notify.getSsid() + "，ssno=" + notify.getSsno());

        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().getSessionBySsid(ssid);
        String path = notify.signPicPath;
        if (session != null) {
            EPro ePro = session.getEPro();
            if (ePro != null) {
                for (EProNotifyListener l : client.getAllListeners()) {
                    l.onSingPicture(ePro.getIp(), path);
                }
            }
        }
    }

    /**
     * @Description EPro指纹图片通知
     * @param:
     * @return:
     * @author: ycw
     * @date:
     */
    private void onFingerPrint(FingerPrintNotify notify){
        log.info("======>EPro指纹图片通知(FingerPrintNotify) ssid=" + notify.getSsid() + "，ssno=" + notify.getSsno());

        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().getSessionBySsid(ssid);
        String path = notify.getFingerPrintPath();
        if (session != null) {
            EPro ePro = session.getEPro();
            if (ePro != null) {
                for (EProNotifyListener l : client.getAllListeners()) {
                    l.onFingerPrint(ePro.getIp(), path);
                }
            }
        }
    }

    /**
     * @Description EPro掉线通知
     * @param:
     * @return:
     * @author: ycw
     * @date:
     */
    private void onEProOffline(LostCntNotify notify) {
        log.error("======>EPro掉线通知(onEProOffLine) ssid=" + notify.getSsid() + "，ssno=" + notify.getSsno());

        int ssid = notify.getSsid();
        EProSession session = client.getSessionManager().removeSession(ssid);
        if (session != null) {
            session.setStatus(EProSessionStatus.DISCONNECT);
            EPro ePro = session.getEPro();
            if (ePro != null) {

                log.error("======>EPro掉线通知(onEProOffLine) ssid=" + notify.getSsid() + "，sson=" + notify.getSsno() + "，ip=" + ePro.getIp() + " 开始重连！");

                String id = ePro.getId();
                client.reStartConnect(id);

                for (EProNotifyListener l : client.getAllListeners()) {
                    l.onEProOffLine(ePro.getId(), ePro.getIp());
                }
            }
        }
    }

}
