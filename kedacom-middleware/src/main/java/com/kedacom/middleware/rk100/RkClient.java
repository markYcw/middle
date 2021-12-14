package com.kedacom.middleware.rk100;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.rk100.domain.RK;
import com.kedacom.middleware.rk100.request.*;
import com.kedacom.middleware.rk100.response.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @ClassName RkClient
 * @Description RK100服务器接口访问。
 * @Author zlf
 * @Date 2021/6/10 9:41
 */
public class RkClient {

    private static final Logger log = LogManager.getLogger(RkClient.class);

    private KM km;

    /*
    会话管理。根据目前的设计，一个RK最多一个会话，好比一个监控平台只有一个主链。
     */
    private RkSessionManager sessionManager;

    /*
    RK信息集合。
    key：RK标识， value:RK详细信息
     */
    private Hashtable<String, RK> rkCacheByID = new Hashtable<String, RK>();

    /*
    服务器数据和状态监听器
     */
    private List<RkNotifyListener> listeners = new ArrayList<RkNotifyListener>();

    /*
    RK连接监控线程
     */
    private RkConnMonitorThread rkConnMonitorThread;

    /**
     * @Description 构造函数
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public RkClient(KM km) {
        this.km = km;
        this.sessionManager = new RkSessionManager(this);
        RkClientListener listener = new RkClientListener(this);

        /*
         * TODO 如果当RkClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
         * 但由于实际应用中RkClient只有一个，所以此处暂不处理
         */
        TCPClient tcpClient = (TCPClient) km.getClient();
        tcpClient.addListener(listener);
    }

    /**
     * @Description 增加监听器
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public boolean addListener(RkNotifyListener listener) {
        return this.listeners.add(listener);
    }

    /**
     * @Description 删除监听器
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public boolean removeListener(RkNotifyListener listener) {
        return this.listeners.remove(listener);
    }

    /**
     * @Description 获取所有监听器
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    protected List<RkNotifyListener> getAllListeners() {
        List<RkNotifyListener> list = new ArrayList<RkNotifyListener>(this.listeners.size());
        list.addAll(listeners);
        return list;
    }

    /**
     * @Description 获取会话管理器
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public RkSessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @Description 增加Rk信息。
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public void addRK(RK rk) {
        this.rkCacheByID.put(rk.getId(), rk);
        this.reStartConnect(rk.getId());
    }

    /**
     * @Description 更新Rk信息
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public void updateRK(RK rk) {
        RK old = this.rkCacheByID.get(rk.getId());
        if (!(old.getIp().equals(rk.getIp())) || old.getPort() != rk.getPort()) {
            // 需要重新连接
            old.update(rk);
            this.logout(rk.getId());
            this.reStartConnect(rk.getId());
        } else {
            old.update(rk);

            // 解决在RkCacheByID中已经有该Rk的信息，但是Rk未连接，如果Rk信息不发生改变，那么Rk再也连接不上
            RkSession session = sessionManager.getSessionByID(rk.getId());
            if (session == null || !session.isLogin()) { // 当前Rk未连接
                this.reStartConnect(rk.getId());
            }
        }
    }

    /**
     * @Description 删除Rk信息。
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    private void removeRK(RK rk) {
        if (rk == null) {
            return;
        }
        String id = rk.getId();

        if (rkConnMonitorThread != null) {
            rkConnMonitorThread.removeRKId(id);
        }

        RkSession session = sessionManager.getSessionByID(id);
        if (session != null) {
            this.logout(id);
        }

        this.rkCacheByID.remove(id);
    }

    /**
     * @Description 设置（添加或修改）Rk信息
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public void setRk(RK rk) {
        RK old = this.rkCacheByID.get(rk.getId());
        if (old != null) {
            updateRK(rk);
        } else {
            addRK(rk);
        }
    }

    /**
     * @Description 根据id删除Rk信息。
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public RK removeRKByID(String id) {
        RK rk = rkCacheByID.get(id);
        this.removeRK(rk);
        return rk;
    }

    private IClient getClient() {
        return km.getClient();
    }

    /**
     * @Description 停止
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public void stop() {
        this.stopReStartConnect();
        this.sessionManager.clear();
    }

    /**
     * @Description 获取会话标识
     * @param: id Rk的标识
     * @return: 如果已连接，返回ssid，否则返回-1
     * @author:zlf
     * @date:
     */
    private int tryGetSSIDByID(String id) throws ConnectException {
        RkSession session = sessionManager.getSessionByID(id);
        if (session == null) {
            throw new ConnectException("RK未连接");
        }
        if (!session.isLogin()) {
            if (session.getStatus() == RkSessionStatus.CONNECTING) {
                throw new ConnectException("RK正在登录中");
            } else if (session.getStatus() == RkSessionStatus.FAILED) {
                throw new ConnectException("RK登录失败");
            } else if (session.getStatus() == RkSessionStatus.DISCONNECT) {
                throw new ConnectException("RK连接中断");
            } else {
                throw new ConnectException("RK未登录");
            }
        }
        int ssid = session.getSsid();
        return ssid;
    }

    /**
     * @Description 获取会话标识（为了登出）
     * @param: id Rk的标识
     * @return: 如果已连接，返回ssid，否则返回-1
     * @author:zlf
     * @date:
     */
    private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
        RkSession session = sessionManager.getSessionByID(id);
        if (session == null) {
            throw new ConnectException("RK未连接");
        }
        return session.getSsid();
    }

    public void onDisConnect(String id) {
        try {
            int ssid = this.tryGetSSIDByID(id);
            sessionManager.removeSession(ssid);
        } catch (ConnectException e) {
            log.debug("onDisConnect: not login", e);
        }
        this.reStartConnect(id);
    }

    /**
     * @Description 在单独的线程中重新连接Rk
     * @param: id Rk的标识
     * @return:
     * @author:zlf
     * @date:
     */
    public void reStartConnect(String id) {
        if (this.isLogin(id)) {
            this.logout(id);
        }
        boolean newThread = false;
        if (rkConnMonitorThread == null || !rkConnMonitorThread.isWork()) {
            rkConnMonitorThread = new RkConnMonitorThread(this);
            rkConnMonitorThread.setTimeout(15000);
            rkConnMonitorThread.setName("Rk-ConnMonitor");
            rkConnMonitorThread.setDaemon(true);
            newThread = true;
        }
        rkConnMonitorThread.addRKId(id);
        if (newThread) {
            rkConnMonitorThread.start();
        }
    }

    private void stopReStartConnect() {
        if (rkConnMonitorThread != null) {
            rkConnMonitorThread.stop();
            rkConnMonitorThread = null;
        }
    }

    /**
     * @Description 返回指定的Rk是否已经登录
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public boolean isLogin(String id) {
        RkSession session = sessionManager.getSessionByID(id);
        boolean login = session != null && session.isLogin();
        return login;
    }

    private void refreshTime(int ssid) {
        RkSession session = sessionManager.getSessionBySsid(ssid);
        if (session != null) {
            session.refreshTime();
        }
    }

    private RkResponse sendRequest(RkRequest request) throws KMException {

        int ssid = request.getSsid();
        this.refreshTime(ssid);

        RkResponse response = (RkResponse) getClient().sendRequest(request);

        int err = response.getErrorcode();
        if (err > 0) {
            //远端返回错误码
            RemoteException e = new RemoteException(String.valueOf(err));
            e.setErrorcode(err);
            throw e;
        }

        return response;
    }

    /**
     * @Description 登录
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public int login(String ip, int port) throws KMException {
        RkSession session = sessionManager.getSessionByIP(ip);
        if (session != null) {
            /*
             * Rk已经登录过。
             * 目前程序设计为，一个Rk只需要登录一次，类似于在platform中只登录一个主链。
             */
            return session.getSsid();
        }
        int ssid = this.login0(ip, port);
        if (ssid > 0) {
            //登录成功
            session = new RkSession();
            session.setSsid(ssid);
            sessionManager.putSession(session);
        }
        return ssid;
    }

    /**
     * @Description 根据ID登录Rk
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public int login(String id) throws KMException {
        RK rk = rkCacheByID.get(id);
        if (rk == null) {
            throw new DataException("Rk信息不存在,ID=" + id);
        }
        return this.loginByRk(rk);
    }

    /**
     * @Description 最终实现登录的方法
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    private int loginByRk(RK rk) throws KMException {

        String id = rk.getId();
        String ip = rk.getIp();
        int port = rk.getPort();

        RkSession session = sessionManager.getSessionByID(id);
        if (session != null) {
            //已登录
            return session.getSsid();
        }

        int ssid = this.login(ip, port);
        if (ssid > 0) {
            //登录成功
            session = new RkSession();
            session.setSsid(ssid);
            session.setLastTime(System.currentTimeMillis());
            session.setStatus(RkSessionStatus.CONNECTED);
            session.setRk(rk);
            sessionManager.putSession(session);
        }

        return ssid;
    }

    private int login0(String ip, int port) throws KMException {
        LoginRequest request = new LoginRequest();
        request.setIp(ip);
        request.setPort(port);

        LoginResponse response = (LoginResponse) this.sendRequest(request);
        int ssid = response.getSsid();
        return ssid;
    }

    /**
     * @Description 登出Rk
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public int logout(String id) {
        int ssid = -1;
        try {
            ssid = this.tryGetSSIDByIDForLogout(id);
        } catch (ConnectException e) {
            log.debug("logout:not login", e);
        }

        if (ssid >= 0) {
            RkSession session = sessionManager.removeSession(ssid);
            if (session != null && session.isLogin()) {
                LogoutRequest request = new LogoutRequest();
                request.setSsid(ssid);
                try {
                    RkResponse rkResponse = this.sendRequest(request);
                    return rkResponse.getErrorcode();
                } catch (KMException e) {
                    log.warn("logout failed", e);
                }
            }
        }
        return -1;
    }

    /**
     * @Description 获取RK100信息
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public ListRkResponse listRkInfos(RK rk) throws KMException {
        int ssid = loginByRk(rk);
        ListRkRequest request = new ListRkRequest();
        request.setSsid(ssid);

        ListRkResponse response = (ListRkResponse) this.sendRequest(request);
        return response;
    }

    /**
     * @Description 发起开关请求
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public int devOnOff(RK rk, String deviceSn, String data, String componentId) throws KMException {
        int ssid = loginByRk(rk);
        DevOnOffRequest request = new DevOnOffRequest();
        request.setSsid(ssid);
        request.setData(data);
        request.setComponentId(componentId);
        request.setDeviceSn(deviceSn);

        DevOnOffResponse response = (DevOnOffResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * @Description 获取所有开关状态
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public List<GetAllLiveStateDetailResponse> getAllLiveState(RK rk, String deviceSn) throws KMException {
        int ssid = loginByRk(rk);
        GetAllLiveStateRequest request = new GetAllLiveStateRequest();
        request.setSsid(ssid);
        request.setDeviceSn(deviceSn);

        GetAllLiveStateResponse response = (GetAllLiveStateResponse) this.sendRequest(request);
        return response.getStates();
    }

}
