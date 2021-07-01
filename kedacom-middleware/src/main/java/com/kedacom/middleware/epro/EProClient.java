package com.kedacom.middleware.epro;

import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.epro.domain.EPro;
import com.kedacom.middleware.epro.request.*;
import com.kedacom.middleware.epro.response.*;
import com.kedacom.middleware.exception.ConnectException;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @ClassName EProClient
 * @Description E10Pro服务器接口访问。
 * @Author ycw
 * @Date 2021/06/30 15:18
 */
public class EProClient {

    private static final Logger log = Logger.getLogger(EProClient.class);

    private KM km;

    /*
    会话管理。根据目前的设计，一个E10Pro最多一个会话，好比一个监控平台只有一个主链。
     */
    private EProSessionManager sessionManager;

    /*
    E10Pro信息集合。
    key：E10Pro标识， value:E10Pro详细信息
     */
    private Hashtable<String, EPro> EProCacheByID = new Hashtable<String, EPro>();

    /*
    服务器数据和状态监听器
     */
    private List<EProNotifyListener> listeners = new ArrayList<EProNotifyListener>();

    /*
    E10Pro连接监控线程
     */
    private EProConnMonitorThread eProConnMonitorThread;

    /**
     * @Description 构造函数
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public EProClient(KM km) {
        this.km = km;
        this.sessionManager = new EProSessionManager(this);
        EProClientListener listener = new EProClientListener(this);

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
     * @author:ycw
     * @date:
     */
    public boolean addListener(EProNotifyListener listener) {
        return this.listeners.add(listener);
    }

    /**
     * @Description 删除监听器
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public boolean removeListener(EProNotifyListener listener) {
        return this.listeners.remove(listener);
    }

    /**
     * @Description 获取所有监听器
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    protected List<EProNotifyListener> getAllListeners() {
        List<EProNotifyListener> list = new ArrayList<EProNotifyListener>(this.listeners.size());
        list.addAll(listeners);
        return list;
    }

    /**
     * @Description 获取会话管理器
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public EProSessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @Description 增加E10Pro信息。
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public void addEPro(EPro ePro) {
        this.EProCacheByID.put(ePro.getId(), ePro);
        this.reStartConnect(ePro.getId());
    }

    /**
     * @Description 更新E10Pro信息
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public void updateEPro(EPro ePro) {
        EPro old = this.EProCacheByID.get(ePro.getId());
        if (!(old.getIp().equals(ePro.getIp())) || old.getPort() != ePro.getPort()) {
            // 需要重新连接
            old.update(ePro);
            this.logout(ePro.getId());
            this.reStartConnect(ePro.getId());
        } else {
            old.update(ePro);

            // 解决在EProCacheByID中已经有该E10Pro的信息，但是E10Pro未连接，如果E10Pro信息不发生改变，那么E10Pro再也连接不上
            EProSession session = sessionManager.getSessionByID(ePro.getId());
            if (session == null || !session.isLogin()) { // 当前E10Pro未连接
                this.reStartConnect(ePro.getId());
            }
        }
    }

    /**
     * @Description 删除E10Pro信息。
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    private void removeEPro(EPro ePro) {
        if (ePro == null) {
            return;
        }
        String id = ePro.getId();

        if (eProConnMonitorThread != null) {
            eProConnMonitorThread.removeEProId(id);
        }

        EProSession session = sessionManager.getSessionByID(id);
        if (session != null) {
            this.logout(id);
        }

        this.EProCacheByID.remove(id);
    }

    /**
     * @Description 设置（添加或修改）E10Pro信息
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public void setEPro(EPro ePro) {
        EPro old = this.EProCacheByID.get(ePro.getId());
        if (old != null) {
            updateEPro(ePro);
        } else {
            addEPro(ePro);
        }
    }

    /**
     * @Description 根据id删除E10Pro信息。
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public EPro removeEProByID(String id) {
        EPro ePro = EProCacheByID.get(id);
        this.removeEPro(ePro);
        return ePro;
    }

    private IClient getClient() {
        return km.getClient();
    }

    /**
     * @Description 停止
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public void stop() {
        this.stopReStartConnect();
        this.sessionManager.clear();
    }

    /**
     * @Description 获取会话标识
     * @param: id E10Pro的标识
     * @return: 如果已连接，返回ssid，否则返回-1
     * @author:ycw
     * @date:
     */
    private int tryGetSSIDByID(String id) throws ConnectException {
        EProSession session = sessionManager.getSessionByID(id);
        if (session == null) {
            throw new ConnectException("E10Pro未连接");
        }
        if (!session.isLogin()) {
            if (session.getStatus() == EProSessionStatus.CONNECTING) {
                throw new ConnectException("E10Pro正在登录中");
            } else if (session.getStatus() == EProSessionStatus.FAILED) {
                throw new ConnectException("E10Pro登录失败");
            } else if (session.getStatus() == EProSessionStatus.DISCONNECT) {
                throw new ConnectException("E10Pro连接中断");
            } else {
                throw new ConnectException("E10Pro未登录");
            }
        }
        int ssid = session.getSsid();
        return ssid;
    }

    /**
     * @Description 获取会话标识（为了登出）
     * @param: id E10Pro的标识
     * @return: 如果已连接，返回ssid，否则返回-1
     * @author:ycw
     * @date:
     */
    private int tryGetSSIDByIDForLogout(String id) throws ConnectException {
        EProSession session = sessionManager.getSessionByID(id);
        if (session == null) {
            throw new ConnectException("E10Pro未连接");
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
     * @Description 在单独的线程中重新连接E10Pro
     * @param: id E10Pro的标识
     * @return:
     * @author:zlf
     * @date:
     */
    public void reStartConnect(String id) {
        if (this.isLogin(id)) {
            this.logout(id);
        }
        boolean newThread = false;
        if (eProConnMonitorThread == null || !eProConnMonitorThread.isWork()) {
            eProConnMonitorThread = new EProConnMonitorThread(this);
            eProConnMonitorThread.setTimeout(15000);
            eProConnMonitorThread.setName("Rk-ConnMonitor");
            eProConnMonitorThread.setDaemon(true);
            newThread = true;
        }
        eProConnMonitorThread.addEProId(id);
        if (newThread) {
            eProConnMonitorThread.start();
        }
    }

    private void stopReStartConnect() {
        if (eProConnMonitorThread != null) {
            eProConnMonitorThread.stop();
            eProConnMonitorThread = null;
        }
    }

    /**
     * @Description 返回指定的E10Pro是否已经登录
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public boolean isLogin(String id) {
        EProSession session = sessionManager.getSessionByID(id);
        boolean login = session != null && session.isLogin();
        return login;
    }

    private void refreshTime(int ssid) {
        EProSession session = sessionManager.getSessionBySsid(ssid);
        if (session != null) {
            session.refreshTime();
        }
    }

    private ERroResponse sendRequest(EProRequest request) throws KMException {

        int ssid = request.getSsid();
        this.refreshTime(ssid);

        ERroResponse response = (ERroResponse) getClient().sendRequest(request);

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
     * @author:ycw
     * @date:
     */
    public int login(String ip, int port) throws KMException {
        EProSession session = sessionManager.getSessionByIP(ip);
        if (session != null) {
            /*
             * E10Pro已经登录过。
             * 目前程序设计为，一个E10Pro只需要登录一次，类似于在platform中只登录一个主链。
             */
            return session.getSsid();
        }
        int ssid = this.login0(ip, port);
        if (ssid > 0) {
            //登录成功
            session = new EProSession();
            session.setSsid(ssid);
            sessionManager.putSession(session);
        }
        return ssid;
    }

    /**
     * @Description 根据ID登录E10Pro
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public int login(String id) throws KMException {
        EPro ePro = EProCacheByID.get(id);
        if (ePro == null) {
            throw new DataException("Rk信息不存在,ID=" + id);
        }
        return this.loginByEPro(ePro);
    }

    /**
     * @Description 最终实现登录的方法
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    private int loginByEPro(EPro ePro) throws KMException {

        String id = ePro.getId();
        String ip = ePro.getIp();
        int port = ePro.getPort();

        EProSession session = sessionManager.getSessionByID(id);
        if (session != null) {
            //已登录
            return session.getSsid();
        }

        int ssid = this.login(ip, port);
        if (ssid > 0) {
            //登录成功
            session = new EProSession();
            session.setSsid(ssid);
            session.setLastTime(System.currentTimeMillis());
            session.setStatus(EProSessionStatus.CONNECTED);
            session.setEPro(ePro);
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
     * @Description 登出E10Pro
     * @param:
     * @return:
     * @author:ycw
     * @date:
     */
    public void logout(String id) {
        int ssid = -1;
        try {
            ssid = this.tryGetSSIDByIDForLogout(id);
        } catch (ConnectException e) {
            log.debug("logout:not login", e);
        }

        if (ssid >= 0) {
            EProSession session = sessionManager.removeSession(ssid);
            if (session != null && session.isLogin()) {
                LogoutRequest request = new LogoutRequest();
                request.setSsid(ssid);
                try {
                    this.sendRequest(request);
                } catch (KMException e) {
                    log.warn("logout failed", e);
                }
            }
        }

    }


    /**
     * 下发pdf文件
     * @param ePro
     * @param filepath pdf文件路径
     * @throws KMException
     */
    public void postPdf(EPro ePro, String filepath) throws KMException {
        int ssid = loginByEPro(ePro);
        PostPdfRequest request = new PostPdfRequest();
        request.setSsid(ssid);
        request.setFilepath(filepath);

        this.sendRequest(request);

    }

    /**
     * 手动获取指纹图片
     * @param ePro
     * @return
     * @throws KMException
     */
    public String getFingerPrint(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        GetFingerPrintRequest request = new GetFingerPrintRequest();
        request.setSsid(ssid);

        GetFingerPrintResponse response = (GetFingerPrintResponse) this.sendRequest(request);
        return response.getFingerprint_path();
    }

    /**
     * 打开设备相机
     * @param ePro
     * @throws KMException
     */
    public void openCamera(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        OpenCameraRequest request = new OpenCameraRequest();
        request.setSsid(ssid);

        this.sendRequest(request);

    }

    /**
     * 关闭设备相机
     * @param ePro
     * @throws KMException
     */
    public void closeCamera(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        CloseCameraRequest request = new CloseCameraRequest();
        request.setSsid(ssid);

        this.sendRequest(request);

    }

    /**
     * 抓拍图片
     * @param ePro
     * @throws KMException
     */
    public void capture(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        CaptureRequest request = new CaptureRequest();
        request.setSsid(ssid);

        this.sendRequest(request);

    }

    /**
     * 启动录像
     * @param ePro
     * @throws KMException
     */
    public void startRec(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        StartRecRequest request = new StartRecRequest();
        request.setSsid(ssid);

        this.sendRequest(request);

    }

    /**
     * 停止录像
     * @param ePro
     * @throws KMException
     */
    public void stopRec(EPro ePro) throws KMException {
        int ssid = loginByEPro(ePro);
        StopRecRequest request = new StopRecRequest();
        request.setSsid(ssid);

        this.sendRequest(request);

    }


}
