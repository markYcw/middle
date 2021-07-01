package com.kedacom.middleware.epro;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.domain.EPro;
import com.kedacom.middleware.epro.notify.LostCntNotify;
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
