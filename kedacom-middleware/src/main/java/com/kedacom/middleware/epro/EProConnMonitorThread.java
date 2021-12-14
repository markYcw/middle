package com.kedacom.middleware.epro;

import keda.common.util.ATaskThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName EProConnMonitorThread
 * @Description E10Pro连接监控线程
 * @Author ycw
 * @Date 2021/06/30 15:12
 */
public class EProConnMonitorThread extends ATaskThread {

    private static final Logger log = LogManager.getLogger(EProConnMonitorThread.class);

    private EProClient client;

    private Set<String> ids = new HashSet<String>();

    private int idleCount = 0; // 空闲周期

    public EProConnMonitorThread(EProClient client) {
        this.client = client;
    }

    @Override
    public void doWork() throws Exception {
        Set<String> _ids;
        synchronized (this.ids) {
            _ids = new HashSet<String>(this.ids);
        }
        Set<String> success = new HashSet<String>();
        for (String id : _ids) {
            boolean ret = false;
            try {
                int ssid = client.login(id);
                if (ssid >= 0) {
                    success.add(id);
                    ret = true;
                }
            } catch (Exception e) {
                log.error("(EProId=" + id + ")" + e.getMessage(), e);
            }

            if (!ret) {
                EProSession session = client.getSessionManager().getSessionByID(id);
                if (session != null) {
                    session.setStatus(EProSessionStatus.FAILED);
                }
            }
        }

        synchronized (this.ids) {
            this.ids.removeAll(success);
            if (this.ids.size() <= 0) {
                //全部连接完成
                idleCount++;
                if (idleCount >= 3) {
                    //空闲三个周期，自动退出
                    super.stop();
                }
            } else {
                idleCount = 0;
            }
        }
    }

    public void addEProId(String id) {
        synchronized (ids) {
            this.ids.add(id);
        }
    }

    public void removeEProId(String id) {
        synchronized (ids) {
            this.ids.remove(id);
        }
    }
}
