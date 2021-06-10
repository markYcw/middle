package com.kedacom.middleware.rk100;

import com.kedacom.middleware.rk100.domain.RK;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @ClassName RkSessionManager
 * @Description RK100会话管理
 * @Author zlf
 * @Date 2021/6/10 9:45
 */
public class RkSessionManager {
    /**
     * 会话集。
     * key : 会话标识; value ：会话
     */
    private Hashtable<Integer, RkSession> sessions = new Hashtable<Integer, RkSession>();

    private RkClient RkClient;
    private boolean sessionTimeoutEnable = true;//是否支持会话超时，默认true(支持).
    private RkSessionTimeoutManager timeoutManager;

    public RkSessionManager(RkClient RkClient) {
        this.RkClient = RkClient;
    }

    protected RkClient getRkClient() {
        return RkClient;
    }

    public boolean isSessionTimeoutEnable() {
        return sessionTimeoutEnable;
    }

    public void setSessionTimeoutEnable(boolean sessionTimeoutEnable) {
        this.sessionTimeoutEnable = sessionTimeoutEnable;
    }

    private void startTimeoutManager() {
        if (sessionTimeoutEnable) {
            if (timeoutManager == null) {
                timeoutManager = new RkSessionTimeoutManager(this);
                timeoutManager.setDaemon(true);
                timeoutManager.setName("Rk-SessionManager");
                timeoutManager.setTimeout(30000);//30秒检测一次
                timeoutManager.start(60000);//延迟60秒启动
            }
        }
    }

    private void stopTimeoutManager() {
        if (timeoutManager != null) {
            timeoutManager.stop();
        }
    }

    /**
     * 增加会话
     *
     * @param session
     * @return
     * @see #removeSession(int)
     */
    public synchronized RkSession putSession(RkSession session) {
        int ssid = session.getSsid();
        session = sessions.put(ssid, session);

        this.startTimeoutManager();

        return session;
    }

    /**
     * 删除会话
     *
     * @param ssid 会话标识
     * @return
     * @see #putSession(RkSession)
     */
    public synchronized RkSession removeSession(int ssid) {
        RkSession session = sessions.remove(ssid);
        if (sessions.size() <= 0) {
            this.stopTimeoutManager();
        }
        return session;
    }

    /**
     * 清空所有会话
     */
    public synchronized void clear() {
        this.sessions.clear();
        this.stopTimeoutManager();
    }

    /**
     * 获取Rk连接会话。
     *
     * @param ip RkIP地址
     * @return 如果已登录此Rk，返回会话，否则返回null
     */
    public RkSession getSessionByIP(String ip) {
        RkSession session = null;
        for (RkSession s : sessions.values()) {
            RK rk = s.getRk();
            String rkIp = rk != null ? rk.getIp() : null;
            if (rkIp != null && rkIp.equalsIgnoreCase(ip)) {
                session = s;
                break;
            }
        }
        return session;
    }

    /**
     * 获取连接会话。
     *
     * @param id
     * @return
     */
    public RkSession getSessionByID(String id) {
        RkSession session = null;
        for (RkSession s : sessions.values()) {
            RK rk = s.getRk();
            String RkId = rk != null ? rk.getId() : null;
            if (RkId != null && RkId.equals(id)) {
                session = s;
                break;
            }
        }
        return session;
    }


    /**
     * 获取Rk连接会话。
     *
     * @param ssid 会话标识
     * @return 如果已登录此Rk，返回会话，否则返回null
     */
    public RkSession getSessionBySsid(int ssid) {
        return sessions.get(ssid);
    }

    public List<RkSession> getAllSessions() {
        List<RkSession> list = new ArrayList<RkSession>(sessions.size());
        list.addAll(sessions.values());
        return list;
    }
}

/**
 * 会话超时管理
 *
 * @author LinChaoYu
 */
class RkSessionTimeoutManager extends ATaskThread {
    /**
     * 超时时间，单位毫秒。默认5分钟
     */
    private long timeout = 5 * 60 * 1000;
    RkSessionManager sessionManager;

    public RkSessionTimeoutManager(RkSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void doWork() throws Exception {

        List<RkSession> sessions = sessionManager.getAllSessions();
        for (RkSession session : sessions) {
            long lastTime = session.getLastTime();
            long currTime = System.currentTimeMillis();
            if (currTime - lastTime >= timeout) {
                //会话超时
                String id = session.getRk().getId();
                sessionManager.getRkClient().logout(id);
            }
        }

    }
}
