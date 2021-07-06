package com.kedacom.middleware.epro;

import com.kedacom.middleware.epro.domain.EPro;
import keda.common.util.ATaskThread;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @ClassName EProSessionManager
 * @Description E10Pro会话管理
 * @Author ycw
 * @Date 2021/06/30 14:11
 */
public class EProSessionManager {
    /**
     * 会话集。
     * key : 会话标识; value ：会话
     */
    private Hashtable<Integer, EProSession> sessions = new Hashtable<Integer, EProSession>();

    private EProClient eProClient;
    private boolean sessionTimeoutEnable = false;//是否支持会话超时，默认true(支持).
    private EProSessionTimeoutManager timeoutManager;

    public EProSessionManager(EProClient eProClient) {
        this.eProClient = eProClient;
    }

    protected EProClient getEProClient() {
        return eProClient;
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
                timeoutManager = new EProSessionTimeoutManager(this);
                timeoutManager.setDaemon(true);
                timeoutManager.setName("EPro-SessionManager");
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
    public synchronized EProSession putSession(EProSession session) {
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
     * @see #putSession(EProSession)
     */
    public synchronized EProSession removeSession(int ssid) {
        EProSession session = sessions.remove(ssid);
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
     * 获取E10Pro连接会话。
     *
     * @param ip E10ProIP地址
     * @return 如果已登录此E10Pro，返回会话，否则返回null
     */
    public EProSession getSessionByIP(String ip) {
        EProSession session = null;
        for (EProSession s : sessions.values()) {
            EPro ePro = s.getEPro();
            String eProIp = ePro != null ? ePro.getIp() : null;
            if (eProIp != null && eProIp.equalsIgnoreCase(ip)) {
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
    public EProSession getSessionByID(String id) {
        EProSession session = null;
        for (EProSession s : sessions.values()) {
            EPro ePro = s.getEPro();
            String RkId = ePro != null ? ePro.getId() : null;
            if (RkId != null && RkId.equals(id)) {
                session = s;
                break;
            }
        }
        return session;
    }


    /**
     * 获取E10Pro连接会话。
     *
     * @param ssid 会话标识
     * @return 如果已登录此E10Pro，返回会话，否则返回null
     */
    public EProSession getSessionBySsid(int ssid) {
        return sessions.get(ssid);
    }

    public List<EProSession> getAllSessions() {
        List<EProSession> list = new ArrayList<EProSession>(sessions.size());
        list.addAll(sessions.values());
        return list;
    }
}

/**
 * 会话超时管理
 *
 * @author LinChaoYu
 */
class EProSessionTimeoutManager extends ATaskThread {
    /**
     * 超时时间，单位毫秒。默认5分钟
     */
    private long timeout = 5 * 60 * 1000;
    EProSessionManager sessionManager;

    public EProSessionTimeoutManager(EProSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void doWork() throws Exception {

        List<EProSession> sessions = sessionManager.getAllSessions();
        for (EProSession session : sessions) {
            long lastTime = session.getLastTime();
            long currTime = System.currentTimeMillis();
            if (currTime - lastTime >= timeout) {
                //会话超时
                String id = session.getEPro().getId();
                sessionManager.getEProClient().logout(id);
            }
        }

    }
}
