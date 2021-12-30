package com.kedacom.middleware.rk100;

import com.kedacom.middleware.client.INotify;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.client.TCPClientListenerAdapter;
import com.kedacom.middleware.epro.EProSession;
import com.kedacom.middleware.rk100.domain.RK;
import com.kedacom.middleware.rk100.notify.LostCntNotify;


import org.apache.log4j.Logger;

import java.util.List;

/**
 * @ClassName RkClientListener
 * @Description 会话RK100事件监听器
 * @Author zlf
 * @alterby ycw 2021/7/15 17:17
 * @Date 2021/6/10 9:43
 */
public class RkClientListener extends TCPClientListenerAdapter {

    private static final Logger log = Logger.getLogger(RkClientListener.class);

    private RkClient client;

    public RkClientListener(RkClient client) {
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
        RkSession session = client.getSessionManager().getSessionBySsid(ssid);
        String ip = null;
        if (session != null && session.getRk() != null) {
            ip = session.getRk().getIp();
        } else {
            log.warn("无效的会话,ssid=" + ssid);
            return;
        }

        if (ip == null) {
            log.warn("会话中无有效的SVR信息,ssid=" + ssid);
            return;
        }
        if (notify instanceof LostCntNotify) {
            // 终端掉线
            this.onRkOffline((LostCntNotify) notify);
        }
    }

    @Override
    public void onClosed(TCPClient client) {
        this.onAllOffine();
    }
    @Override
    public void onInterrupt(TCPClient client) {
        this.onAllOffine();
    }

    /**
     * 全部RK100下线
     */
    private void onAllOffine(){
        List<RkSession> sessions = client.getSessionManager().getAllSessions();
        for(RkSession session : sessions){
            int ssid = session.getSsid();
            client.getSessionManager().removeSession(ssid);
        }
    }


    /**
     * @Description Rk掉线通知
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    private void onRkOffline(LostCntNotify notify) {
        log.error("======>Rk掉线通知(onRkOffine) ssid=" + notify.getSsid() + "，sson=" + notify.getSsno());

        int ssid = notify.getSsid();
        RkSession session = client.getSessionManager().removeSession(ssid);
        if (session != null) {
            session.setStatus(RkSessionStatus.DISCONNECT);
            RK rk = session.getRk();
            if (rk != null) {

                log.error("======>Rk掉线通知(onRkOffine) ssid=" + notify.getSsid() + "，sson=" + notify.getSsno() + "，ip=" + rk.getIp() + " 开始重连！");

                String id = rk.getId();
                client.reStartConnect(id);

                for (RkNotifyListener l : client.getAllListeners()) {
                    l.onRKOffLine(rk.getId(), rk.getIp());
                }
            }
        }
    }

}
