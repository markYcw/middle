package com.kedacom.middleware.svr;

import com.kedacom.middleware.DeviceType;
import com.kedacom.middleware.KM;
import com.kedacom.middleware.client.IClient;
import com.kedacom.middleware.client.TCPClient;
import com.kedacom.middleware.exception.DataException;
import com.kedacom.middleware.exception.KMException;
import com.kedacom.middleware.exception.RemoteException;
import com.kedacom.middleware.svr.domain.*;
import com.kedacom.middleware.svr.request.*;
import com.kedacom.middleware.svr.response.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * SVR接口访问。
 *
 * @author DengJie
 */
public class SVRClient {
    private static final Logger log = Logger.getLogger(SVRClient.class);
    private static KM km;

    /**
     * 会话管理。根据目前的设计，一个SVR最多一个会话，好比一个监控平台只有一个主链。
     */

    private SVRSessionManager sessionManager;
    /**
     * SVR信息集合。 key：终端标识， value:终端详细信息
     */
    private Hashtable<String, SVR> svrCacheByIP = new Hashtable<String, SVR>();
    /**
     * 服务器数据和状态监听器
     */
    private List<SVRNotifyListener> listeners = new ArrayList<SVRNotifyListener>();

    /**
     * SVR连接监控线程
     */
    private SVRConnMonitorThread svrConnMonitorThread;

    public SVRClient(KM km) {
        this.km = km;

        this.sessionManager = new SVRSessionManager(this);
        SVRClientListener listener = new SVRClientListener(this);

        /*
         * TODO 如果当SVRClient被废弃时，没有正常删除监听器，这有可能有未知的风险。
         * 但由于实际应用中SVRClient只有一个，所以此处暂不处理
         */
        TCPClient tcpClient = (TCPClient) km.getClient();
        tcpClient.addListener(listener);

    }

    /**
     * 增加监听器
     *
     * @param listener
     * @return
     * @see #removeListener(SVRNotifyListener)
     */
    public boolean addListener(SVRNotifyListener listener) {
        return this.listeners.add(listener);
    }

    /**
     * 删除监听器
     *
     * @param listener
     * @return
     * @see #addListener(SVRNotifyListener)
     */
    public boolean removeListener(SVRNotifyListener listener) {
        return this.listeners.remove(listener);
    }

    protected List<SVRNotifyListener> getAllListeners() {
        List<SVRNotifyListener> list = new ArrayList<SVRNotifyListener>(
                this.listeners.size());
        list.addAll(listeners);
        return list;
    }

    /**
     * 获取会话管理器
     *
     * @return
     */
    public SVRSessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * 设置（添加或修改）SVR信息
     *
     * @param svr
     */
    public void setSVR(SVR svr) {
        SVR old = this.svrCacheByIP.get(svr.getIp());
        if (old != null) {
            updateSVR(svr, true);
        } else {
            addSVR(svr, true);
        }
    }

    /**
     * 增加SVR信息。 默认不开启SVR连接（适配老接口）
     *
     * @param svr
     */
    public void addSVR(SVR svr) {
        addSVR(svr, false);
    }

    /**
     * 增加SVR信息。
     *
     * @param svr
     * @param isReStart 标识是否立即连接SVR
     */
    public void addSVR(SVR svr, boolean isReStart) {
        this.svrCacheByIP.put(svr.getIp(), svr);
        if (isReStart)
            this.reStartConnect(svr.getIp());
    }

    /**
     * 更新SVR信息 默认不开启SVR连接（适配老接口）
     */
    public void updateSVR(SVR svr) {
        updateSVR(svr, false);
    }

    /**
     * 更新SVR信息
     */
    public void updateSVR(SVR svr, boolean isReStart) {
        SVR old = this.svrCacheByIP.get(svr.getIp());
        if (old != null) {
            if (!(old.getIp().equals(svr.getIp()))
                    || !(old.getUsername().equals(svr.getUsername()))
                    || !(old.getPassword().equals(svr.getPassword()))) {
                // 需要重新连接
                old.update(svr);
                if (isReStart)
                    this.reStartConnect(svr.getIp());
                else
                    this.logout(svr.getIp());
            } else {
                old.update(svr);

                // 解决在svrCacheByIP中已经有该SVR的信息，但是SVR未连接，如果SVR信息不发送改变，那么SVR再也连接不上
                if (isReStart) {
                    SVRSession session = sessionManager.getSessionByIP(svr
                            .getIp());
                    if (session == null || !session.isLogin()) {// MT未连接
                        this.reStartConnect(svr.getIp());
                    }
                }
            }
        }
    }

    /**
     * 删除终端信息。
     *
     * @return
     */
    private void removeSVR(SVR svr) {
        if (svr == null) {
            return;
        }
        String ip = svr.getIp();

        if (svrConnMonitorThread != null) {
            svrConnMonitorThread.removeSVRByIp(ip);
        }

        SVRSession session = sessionManager.getSessionByIP(ip);
        if (session != null) {
            this.logout(ip);
        }

        this.svrCacheByIP.remove(ip);
    }

    /**
     * 删除会话平台信息。
     *
     * @param ip
     * @return
     * @see #addSVR(SVR)
     * @see #updateSVR(SVR)
     */
    public SVR removeSVRByIp(String ip) {
        SVR svr = svrCacheByIP.get(ip);
        this.removeSVR(svr);
        return svr;
    }

    private IClient getClient() {
        return km.getClient();
    }

    /**
     * 停止
     */
    public void stop() {
        this.sessionManager.clear();
    }

    /**
     * 返回指定的SVR是否已经登录
     *
     * @param ip
     * @return
     */
    public boolean isLogin(String ip) {
        SVRSession session = sessionManager.getSessionByIP(ip);
        return session != null;
    }

    private void refreshTime(int ssid) {
        SVRSession session = sessionManager.getSessionBySsid(ssid);
        if (session != null) {
            session.refreshTime();
        }
    }

    private SVRResponse sendRequest(SVRRequest request) throws KMException {

        int ssid = request.getSsid();
        this.refreshTime(ssid);

        SVRResponse response = (SVRResponse) getClient().sendRequest(request);

        int err = response.getErrorcode();
        if (err > 0) {
            // 远端返回错误码
            RemoteException e = new RemoteException("错误码:" + err);
            e.setErrorcode(err);
            throw e;
        }

        return response;
    }

    /**
     * 登录
     *
     * @param ip   SVRIP
     * @param port SVR端口
     * @param user 用户名
     * @param pwd  密码
     * @return 登录成功返回会话ID，登录失败返回-1或抛出异常
     * @throws KMException
     * @see #login(String)
     * @see #logout(String)
     */
    public int login(String ip, int port, String user, String pwd, int devType)
            throws KMException {

        SVRSession session = sessionManager.getSessionByIP(ip);
        if (session != null) {
            /*
             * 终端已经登录过。 目前程序设计为，一个终端只需要登录一次，类似于在kplatform中只登录一个主链。
             */
            return session.getSsid();
        }

        int ssid = this.login0(ip, port, user, pwd, devType);
        if (ssid > 0) {
            // 登录成功
            session = new SVRSession();
            session.setSsid(ssid);
            sessionManager.putSession(session);
        }

        return ssid;
    }

    /**
     * 根据IP登录SVR
     *
     * @param ip
     * @return
     * @throws KMException
     * @see # login(String, String, String, String)
     * @see #logout(String)
     */
    public int login(String ip) throws KMException {
        SVR svr = svrCacheByIP.get(ip);
        if (svr == null) {
            throw new DataException("SVR信息不存在,IP=" + ip);
        }
        return this.loginBySVR(svr);
    }

    // 最终实现登录的方法
    private int loginBySVR(SVR svr) throws KMException {

        String ip = svr.getIp();
        int port = svr.getPort();
        String user = svr.getUsername();
        String pwd = svr.getPassword();
        int devType = svr.getDevType();

        SVRSession session = sessionManager.getSessionByIP(ip);
        if (session != null) {
            // 已登录
            return session.getSsid();
        }
        int ssid = this.login(ip, port, user, pwd, devType);
        if (ssid > 0) {
            // 登录成功
            session = new SVRSession();
            session.setSsid(ssid);
            session.setLastTime(System.currentTimeMillis());
            session.setSvr(svr);
            session.setStatus(SVRSessionStatus.CONNECTED);
            sessionManager.putSession(session);

        }

        log.debug("已登录SVR：ip=" + ip + "name=" + svr.getName());
        return ssid;
    }

    /**
     * 向中间件获取SVR连接ssid
     *
     * @param ip   IP地址
     * @param port 端口
     * @param user 用户
     * @param pwd  密码
     * @return
     * @throws KMException
     */
    public int getSvrSSid(String ip, int port, String user, String pwd, int devType)
            throws KMException {

        log.debug("=====> 获取SVR连接索引（getSvrSSid），ip：" + ip + "，port：" + port + "，user：" + user + "，pwd：" + pwd);

        int ssid = this.login0(ip, port, user, pwd, devType);

        log.debug("=====> 获取SVR连接索引（getSvrSSid），ip：" + ip + "，port：" + port + "，user：" + user + "，pwd：" + pwd);

        return ssid;
    }

    private int login0(String ip, int port, String username, String password, int devType)
            throws KMException {

        LoginRequest request = new LoginRequest();
        request.setIp(ip);
        request.setPort(port);
        request.setUser(username);
        request.setPwd(password);
        // 当devType为1时，设定类型为2931的类型，其他的都还是svr默认的类型
        request.setDevtype(1 == devType ? DeviceType.SVR_NVRV7.getValue() : DeviceType.SVR.getValue());

        LoginResponse response = (LoginResponse) this.sendRequest(request);
        int ssid = response.getSsid();
        return ssid;
    }

    /**
     * 获取会话标识。
     *
     * @param ip SVRIP地址
     * @return 如果SVR已连接，返回ssid，否则返回-1
     */
    public int getSSIDByIp(String ip) {
        SVRSession session = sessionManager.getSessionByIP(ip);
        return session != null ? session.getSsid() : -1;
    }

    /**
     * 在单独的线程中重新连接终端
     *
     * @param ip SVRIp
     */
    public void reStartConnect(String ip) {

        if (this.isLogin(ip)) {
            this.logout(ip);
        }

        boolean newThread = false;
        if (svrConnMonitorThread == null || !svrConnMonitorThread.isWork()) {
            svrConnMonitorThread = new SVRConnMonitorThread(this);
            svrConnMonitorThread.setTimeout(15000);
            svrConnMonitorThread.setName("SVR-ConnMonitor");
            svrConnMonitorThread.setDaemon(true);
            newThread = true;
        }
        svrConnMonitorThread.addSVRByIp(ip);
        if (newThread) {
            svrConnMonitorThread.start();
        }
    }

    /**
     * 注销SVR连接索引SSid
     *
     * @param ssid 终端连接索引
     */
    public void logoutSSid(int ssid) {

        log.debug("=====> 注销SVR连接索引（logoutSSid），ssid：" + ssid);

        if (ssid == -1) {
            log.error("=====> 注销SVR连接索引（logoutSSid），ssid：" + ssid + " 失败，无效索引！");
            return;
        }

        LogoutRequest request = new LogoutRequest();
        request.setSsid(ssid);
        try {
            this.sendRequest(request);
        } catch (KMException e) {
            log.error("=====> 注销SVR连接索引（logoutSSid），ssid：" + ssid + " 异常：", e);
        }
    }

    /**
     * 登出终端
     *
     * @param ip
     * @return
     */
    public void logout(String ip) {

        if (!this.isLogin(ip)) {
            // 未登录
            return;
        }

        SVRSession session = sessionManager.getSessionByIP(ip);
        if (session == null) {
            // 未来登录
            return;
        }
        int ssid = session.getSsid();
        sessionManager.removeSession(ssid);
        LogoutRequest request = new LogoutRequest();
        request.setSsid(ssid);
        try {
            this.sendRequest(request);
        } catch (KMException e) {
            log.warn("logout faild", e);
        }
    }

    /**
     * 添加和开启远程点
     *
     * @param svr
     * @param rpname 远程点名称，不能为空，长度限制为32字节（不包含结束符）
     * @param ip     远程点IP地址
     * @param rate   码率
     * @param dual   是否开启双流。false表示否
     * @return
     * @throws KMException
     */
    public int remotePointOn(SVR svr, String rpname, String url, String ip, int rate, boolean dual) throws KMException {
        int ssid = loginBySVR(svr);
        return remotePointOn(ssid, rpname, url, ip, rate, dual);
    }

    /*
    添加和开启远程点
     */
    public int remotePointOn(int ssid, String rpname, String url, String ip, int rate, boolean dual) {
        try {
            RemotePointOnRequest request = new RemotePointOnRequest();
            request.setSsid(ssid);
            request.setIp(ip);
            request.setUrl(url);
            request.setRate(rate);
            request.setDual(dual);
            request.setRpname(rpname);
            RemotePointOnResponse response = (RemotePointOnResponse) this.sendRequest(request);
            return response.getErrorcode();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("添加和开启远程点异常" + e.getMessage());
        }
        return 0;
    }

    /**
     * 停用和删除远程点
     *
     * @param svr
     * @param rpname 远程点名称，不能为空，长度限制为32字节（不包含结束符）
     * @param ip     远程点IP地址
     * @param rate   码率
     * @throws KMException
     */
    public void remotePointOff(SVR svr, String rpname, String url, String ip, int rate) throws KMException {
        int ssid = loginBySVR(svr);
        remotePointOff(ssid, rpname, url, ip, rate);
    }

    /*
    停用和删除远程点
     */
    public void remotePointOff(int ssid, String rpname, String url, String ip, int rate) throws KMException {
        RemotePointOffRequest request = new RemotePointOffRequest();
        request.setSsid(ssid);
        request.setUrl(url);
        request.setIp(ip);
        request.setRate(rate);
        request.setRpname(rpname);

        this.sendRequest(request);//RemotePointOffResponse
    }

    /**
     * 开始录像
     *
     * @param svr      信息
     * @param mode     刻录模式
     * @param burnname 刻录名称
     * @return
     * @throws KMException
     */
    public int startBurn(SVR svr, int mode, String burnname) throws KMException {
        int ssid = loginBySVR(svr);
        StartBurnRequest request = new StartBurnRequest();
        request.setSsid(ssid);
        request.setMode(mode);
        request.setBurnname(burnname);
        StartBurnResponse response = (StartBurnResponse) this
                .sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * 追加刻录
     *
     * @param svr
     * @param filepathname 追加刻录的本地文件路径名
     * @param type         0:文件名全路径  1:文件夹全路径
     * @throws KMException
     * @author ycw
     */
    public int appendBurn(SVR svr, String filepathname, int type, String dirname) throws KMException {
        int ssid = loginBySVR(svr);
        AppendBurnRequest request = new AppendBurnRequest();
        request.setSsid(ssid);
        request.setType(type);
        request.setFilepathname(filepathname);
        request.setDirname(dirname);

        AppendBurnResponse response = (AppendBurnResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * 停止刻录
     *
     * @param svr
     * @return
     * @throws KMException
     */
    public int stopBurn(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        StopBurnRequest request = new StopBurnRequest();
        request.setSsid(ssid);

        StopBurnResponse response = (StopBurnResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * 获取SVR时间
     *
     * @param svr
     * @return
     * @throws KMException
     * @author ycw
     */
    public String getBurnTime(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetBurnTimeRequest request = new GetBurnTimeRequest();
        request.setSsid(ssid);

        GetBurnTimeResponse response = (GetBurnTimeResponse) this.sendRequest(request);
        return response.getTime();
    }

    /**
     * 9.补刻(查询+补录)
     *
     * @param svr
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param mode      刻录模式 （选择DVD的模式）
     * @return
     * @throws KMException
     * @author ycw
     */
    public int supplementBurn(SVR svr, String starttime, String endtime, int mode) throws KMException {
        int ssid = loginBySVR(svr);
        SupplementBurnRequest request = new SupplementBurnRequest();
        request.setSsid(ssid);
        request.setStarttime(starttime);
        request.setEndtime(endtime);
        request.setMode(mode);

        SupplementBurnResponse Response = (SupplementBurnResponse) this.sendRequest(request);
        return Response.getBurntaskid();
    }

    /**
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @param mode      刻录模式 （选择DVD的模式）
     * @Description 新建刻录（专职用来生成MP4文件）
     * @return:
     * @author:zlf
     * @date:
     */
    public int createBurn(SVR svr, String starttime, String endtime, int mode) throws KMException {
        int ssid = loginBySVR(svr);
        CreateBurnRequest request = new CreateBurnRequest();
        request.setSsid(ssid);
        request.setStarttime(starttime);
        request.setEndtime(endtime);
        request.setMode(mode);

        CreateBurnResponse Response = (CreateBurnResponse) this.sendRequest(request);
        return Response.getNetId();
    }

    /**
     * @Description 废止刻录
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public int killBurn(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        KillBurnRequest request = new KillBurnRequest();
        request.setSsid(ssid);

        KillBurnResponse Response = (KillBurnResponse) this.sendRequest(request);
        return Response.getErrorcode();
    }

    /**
     * @Description 获取刻录任务
     * @param: starttime    开始时间
     * @param: endtime      结束时间
     * @return:
     * @author:zlf
     * @date:
     */
    public int getBurnTask(SVR svr, String starttime, String endtime) throws KMException {
        int ssid = loginBySVR(svr);
        GetBurnTaskRequest request = new GetBurnTaskRequest();
        request.setSsid(ssid);
        request.setStarttime(starttime);
        request.setEndtime(endtime);

        GetBurnTaskResponse response = (GetBurnTaskResponse) this.sendRequest(request);
        return response.getNum();
    }

    /**
     * 获取svr状态信息
     *
     * @param svr 设备信息
     * @return SvrState svr状态信息对象
     * @throws KMException
     */
    public SvrState getState(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetStateRequest request = new GetStateRequest();
        request.setSsid(ssid);

        GetStateResponse response = (GetStateResponse) this.sendRequest(request);
        return response.getSvrState();
    }

    /**
     * 查询svr录像
     *
     * @param svr
     * @param chnid     查询录像的通道id，0表示合成通道
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @return
     * @throws KMException
     * @author ycw
     */
    public int queryRec(SVR svr, int chnid, String starttime, String endtime) throws KMException {
        int ssid = loginBySVR(svr);
        QueryRecRequest request = new QueryRecRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        request.setStarttime(starttime);
        request.setEndtime(endtime);

        QueryRecResponse response = (QueryRecResponse) this.sendRequest(request);
        return response.getNum();
    }

    /**
     * @Description 获取画面合成设置
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public GetSvrComposePicResponse getSvrComposePic(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetSvrComposePicRequest request = new GetSvrComposePicRequest();
        request.setSsid(ssid);

        GetSvrComposePicResponse response = (GetSvrComposePicResponse) this.sendRequest(request);
        return response;
    }

    /**
     * @param videoresolution 分辨率
     * @param borderwidth     边框宽度
     * @param mergestyle      自定义画面风格所使用的基础画面风格。 2816 1.0不支持
     * @param picinfonum      有效合成通道数
     * @param chnid
     * @param pictype
     * @Description: 设置合成画面
     * @author lzs
     * @date 2019-7-11 上午9:53:45
     * @version V1.0
     */
    public int setSvrComposePic(SVR svr, int videoresolution, int borderwidth, int mergestyle,
                                int picinfonum, int[] chnid, int[] pictype) throws KMException {
        int ssid = 0;
        try {
            ssid = loginBySVR(svr);
            return setPicStyle(ssid, svr, videoresolution, borderwidth, mergestyle, picinfonum, chnid, pictype);
        } catch (RemoteException e) {
            if (50006 == e.getErrorcode()) {//ssid失效
                log.debug("失效的ssid:" + ssid);
                sessionManager.removeSession(ssid);
                ssid = loginBySVR(svr);
                return setPicStyle(ssid, svr, videoresolution, borderwidth, mergestyle, picinfonum, chnid, pictype);
            }
            e.printStackTrace();
            log.debug("设置合成画面异常" + e.getMessage());
            return e.getErrorcode();
        }
    }

    /*
    设置合成画面
     */
    private int setPicStyle(int ssid, SVR svr, int videoresolution, int borderwidth, int mergestyle,
                            int picinfonum, int[] chnid, int[] pictype) throws KMException {
        //int ssid = loginBySVR(svr);
        SetComposePicRequest request = new SetComposePicRequest();
        request.setSsid(ssid);
        request.setVideoresolution(videoresolution);
        request.setBorderwidth(borderwidth);
        request.setMergestyle(mergestyle);
        request.setPicinfonum(picinfonum);
        request.setChnid(chnid);
        request.setPictype(pictype);

        SetComposePicRespose respose = (SetComposePicRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @Description PTZ控制
     * @param: cmd      控制命令Id
     * @param: chnid    通道Id
     * @param: param1   1：向上移动；2：向下移动；3：向左移动；4：向右移动；5：回归；6：停止移动；7：拉近摄像头；8：拉远摄像头；14：摄象头预存；15：调摄象头预存；20：中心定位；21：局部放大；26：局部缩小
     * @return:
     * @author:zlf
     * @date:
     */
    public int ptzctrl(SVR svr, int cmd, int chnid, int param1) throws KMException {
        int ssid = loginBySVR(svr);
        SvrPtzCtrlRequest request = new SvrPtzCtrlRequest();
        request.setSsid(ssid);
        request.setCmd(cmd);
        request.setChnid(chnid);
        request.setParam1(param1);

        SvrPtzCtrlResponse response = (SvrPtzCtrlResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * @Description 发送双流
     * @param: chnid   远程点通道号
     * @param: dual    true表示发送双流，false表示不发送或停止发送
     * @return:
     * @author:zlf
     * @date:
     */
    public int startDual(SVR svr, int chnid, boolean dual) throws KMException {
        int ssid = loginBySVR(svr);
        StartDualRequest request = new StartDualRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        request.setDual(dual);

        StartDualResponse response = (StartDualResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * 下载SVR录像（由于底层SDK的限制，该信令同一时刻只能有一个过程在执行，否则会崩溃）
     *
     * @param svr      设备信息
     * @param chnid    下载录像通道
     * @param stime    开始时间
     * @param etime    结束时间
     * @param path     路径
     * @param filename 文件名
     * @return
     * @throws KMException
     */
    public int startDownloadrec(SVR svr, int chnid, String stime, String etime,
                                String path, String filename) throws KMException {
        int ssid = loginBySVR(svr);
        StartDownloadrecRequest request = new StartDownloadrecRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        request.setStarttime(stime);
        request.setEndtime(etime);
        request.setDownloadfiledir(path);
        request.setDownloadfilename(filename);

        StartDownloadrecResponse response = (StartDownloadrecResponse) this.sendRequest(request);
        return response.getDownloadhandle();
    }

    /**
     * @Description 停止下载SVR录像
     * @param: downloadhandle   下载句柄标示一个下载操作
     * @return:
     * @author:zlf
     * @date:
     */
    public int stopDownloadRec(SVR svr, int downloadhandle) throws KMException {
        int ssid = loginBySVR(svr);
        StopDownloadrecRequest request = new StopDownloadrecRequest();
        request.setSsid(ssid);
        request.setDownloadhandle(downloadhandle);

        StopDownloadrecResponse response = (StopDownloadrecResponse) this.sendRequest(request);
        return response.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @param  isnty
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: voiceActive
     * @Description: 是否开启语音激励状态 ，全局生效，语音激励状态开启/关闭
     * @author lzs
     * @date 2019-9-24 下午4:23:00
     * @version V1.0
     */
    public int voiceActive(SVR svr, boolean isnty) throws KMException {
        int ssid = loginBySVR(svr);
        VoiceActiveRequest request = new VoiceActiveRequest();
        request.setSsid(ssid);
        request.setIsnty(isnty);

        VoiceActiveRespose respose = (VoiceActiveRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: getDecoderNum
     * @Description: 获取解码器能力集
     * @author lzs
     * @date 2019-8-8 上午10:30:24
     * @version V1.0
     */
    public int getDecoderNum(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetDecoderNumRequest request = new GetDecoderNumRequest();
        request.setSsid(ssid);

        GetDecoderNumRespose respose = (GetDecoderNumRespose) this.sendRequest(request);
        return respose.getNum();
    }

    /**
     * @param @param  svr
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: getEncoderNum
     * @Description: 获取编码器能力集
     * @author lzs
     * @date 2019-8-8 上午10:33:54
     * @version V1.0
     */
    public int getEncoderNum(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetEncoderNumRequest request = new GetEncoderNumRequest();
        request.setSsid(ssid);

        GetEncoderNumRespose respose = (GetEncoderNumRespose) this.sendRequest(request);
        return respose.getNum();
    }

    /**
     * @param @param svr
     * @param @param chnid
     * @param @param Devinfo
     * @return void 返回类型
     * @throws
     * @throws KMException
     * @Title: addDecoder
     * @Description: 添加解码通道
     * @author lzs
     * @date 2019-8-7 下午3:13:22
     * @version V1.0
     */
    public int addDecoder(SVR svr, int chnid, Devinfo devinfo) throws KMException {
        try {
            int ssid = loginBySVR(svr);
            if (devinfo != null) {
                AddDecoderRequest request = new AddDecoderRequest();
                request.setSsid(ssid);
                request.setChnid(chnid);
                request.setDevinfo(devinfo);

                AddDecoderRespose respose = (AddDecoderRespose) this.sendRequest(request);
                return respose.getErrorcode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("添加解码通道" + e.getMessage());
        }
        return 0;
    }

    /**
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: deleteDecoder
     * @Description:删除解码通道
     * @author lzs
     * @date 2019-8-7 下午4:06:25
     * @version V1.0
     */
    public int deleteDecoder(SVR svr, int chnid) throws KMException {
        int ssid = loginBySVR(svr);
        DeleteDecoderRequest request = new DeleteDecoderRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        //this.sendRequest(request);

        DeleteDecoderRespose respose = (DeleteDecoderRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: getDecoderMainAndAuxiliary
     * @Description: 获取解码器的解码通道和主辅流
     * @author lzs
     * @date 2019-8-8 下午1:21:35
     * @version V1.0
     */
    public GetDecoderMainAndAuxiliaryRespose getDecoderMainAndAuxiliary(SVR svr, int chnid) throws KMException {
        int ssid = loginBySVR(svr);
        GetDecoderMainAndAuxiliaryRequest request = new GetDecoderMainAndAuxiliaryRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);

        GetDecoderMainAndAuxiliaryRespose respose = (GetDecoderMainAndAuxiliaryRespose) this.sendRequest(request);
        return respose;
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @param  encchnid
     * @param @param  secstream
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: setDecoderMainAndAuxiliary
     * @Description: 设置解码器的解码通道和主辅流
     * @author lzs
     * @date 2019-8-8 下午1:25:23
     * @version V1.0
     */
    public int setDecoderMainAndAuxiliary(SVR svr, int chnid, int encchnid, int secstream) throws KMException {
        try {
            int ssid = loginBySVR(svr);
            SetDecoderMainAndAuxiliaryRequest request = new SetDecoderMainAndAuxiliaryRequest();
            request.setSsid(ssid);
            request.setChnid(chnid);
            request.setEncchnid(encchnid);
            request.setSecstream(secstream);

            SetDecoderMainAndAuxiliaryRespose respose = (SetDecoderMainAndAuxiliaryRespose) this.sendRequest(request);
            return respose.getErrorcode();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("设置解码器的解码通道和主辅流异常" + e.getMessage());
        }
        return 0;
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @param  devinfo
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: AddEncoder
     * @Description: 添加编码通道
     * @author lzs
     * @date 2019-8-8 上午10:16:14
     * @version V1.0
     */
    public int addEncoder(SVR svr, int chnid, Devinfo devinfo) throws KMException {
        int ssid = loginBySVR(svr);
        AddEncoderRequest request = new AddEncoderRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        request.setDevinfo(devinfo);

        AddEncoderRespose respose = (AddEncoderRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: deleteEncoder
     * @Description: 删除编码通道
     * @author lzs
     * @date 2019-8-8 上午10:20:04
     * @version V1.0
     */
    public int deleteEncoder(SVR svr, int chnid) throws KMException {
        int ssid = loginBySVR(svr);
        DeleteEncoderRequest request = new DeleteEncoderRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);

        DeleteEncoderRespose respose = (DeleteEncoderRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @param  preset
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: setEncoderSite
     * @Description: 设置编码器的预置位
     * @author lzs
     * @date 2019-8-8 下午1:54:22
     * @version V1.0
     */
    public int setEncoderSite(SVR svr, int chnid, int preset) throws KMException {
        int ssid = loginBySVR(svr);
        SetEncoderSiteRequest request = new SetEncoderSiteRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);
        request.setPreset(preset);

        SetEncoderSiteRespose respose = (SetEncoderSiteRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * @param @param  svr
     * @param @param  chnid
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: getEncoderSite
     * @Description: 获取编码器的预置位
     * @author lzs
     * @date 2019-8-8 下午1:49:58
     * @version V1.0
     */
    public int getEncoderSite(SVR svr, int chnid) throws KMException {
        int ssid = loginBySVR(svr);
        GetEncoderSiteRequest request = new GetEncoderSiteRequest();
        request.setSsid(ssid);
        request.setChnid(chnid);

        GetEncoderSiteRespose respose = (GetEncoderSiteRespose) this.sendRequest(request);
        return respose.getPreset();
    }

    /**
     * @param @param  svr
     * @param @param  outvideochnid
     * @param @param  h323secvideochnid
     * @param @param  remmergestate
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: setRemotePointVideoSourceRequest
     * @Description: 设置远程点输出视频源和双流视频源
     * @author lzs
     * @date 2019-8-8 下午2:01:47
     * @version V1.0
     */
    public int setRemotePointVideoSourceRequest(SVR svr, int outvideochnid, int h323secvideochnid, int remmergestate) throws KMException {
        try {
            int ssid = loginBySVR(svr);
            SetRemotePointVideoSourceRequest request = new SetRemotePointVideoSourceRequest();
            request.setSsid(ssid);
            request.setOutvideochnid(outvideochnid);
            request.setH323secvideochnid(h323secvideochnid);
            request.setRemmergestate(remmergestate);

            SetRemotePointVideoSourceRespose respose = (SetRemotePointVideoSourceRespose) this.sendRequest(request);
            return respose.getErrorcode();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("设置远程点输出视频源和双流视频源异常" + e.getMessage());
        }
        return 0;
    }

    /**
     * @param @param  svr
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: getRemotePointVideoSourceRequest
     * @Description: 获取远程点输出视频源和双流视频源
     * @author lzs
     * @date 2019-8-8 下午1:59:17
     * @version V1.0
     */
    public RemotePointVideoSourceInfo getRemotePointVideoSourceRequest(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetRemotePointVideoSourceRequest request = new GetRemotePointVideoSourceRequest();
        request.setSsid(ssid);

        GetRemotePointVideoSourceRespose respose = (GetRemotePointVideoSourceRespose) this.sendRequest(request);
        return respose.getRemotePointVideoSourceInfo();
    }

    /**
     * @param @param  svr
     * @param @return
     * @param @throws KMException
     * @return int 返回类型
     * @throws
     * @Title: searchEncoderAnDecoder
     * @Description:搜索svr上的编码器和解码器
     * @author lzs
     * @date 2019-8-20 下午2:14:44
     * @version V1.0
     */
    public int searchEncoderAnDecoder(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        SearchEncoderAndDecoderRequest request = new SearchEncoderAndDecoderRequest();
        request.setSsid(ssid);

        SearchEncoderAndDecoderRespose respose = (SearchEncoderAndDecoderRespose) this.sendRequest(request);
        return respose.getErrorcode();
    }

    /**
     * 下载SVR录像
     *
     * @param svr      设备信息
     * @param chnid    下载录像通道
     * @param stime    开始时间
     * @param etime    结束时间
     * @param path     路径
     * @param filename 文件名
     * @return
     * @throws KMException
     */
//    public int startHBDownloadrec(SVR svr, int chnid, String stime, String etime, String path, String filename) throws KMException {
//        int ssid = this.login0(svr.getIp(), svr.getPort(), svr.getUsername(), svr.getPassword());
//        StartDownloadrecRequest request = new StartDownloadrecRequest();
//        request.setSsid(ssid);
//        request.setChnid(chnid);
//        request.setStarttime(stime);
//        request.setEndtime(etime);
//        request.setDownloadfiledir(path);
//        request.setDownloadfilename(filename);
//
//        StartDownloadrecResponse response = (StartDownloadrecResponse) this.sendRequest(request);
//        return response.getDownloadhandle();
//    }

    /**
     * @Description 获取编码通道列表
     * @param:
     * @return:
     * @author:zlf
     * @date:
     */
    public List<SvrEncChnList> getEncChnList(SVR svr) throws KMException {
        int ssid = loginBySVR(svr);
        GetEncChnListRequest request = new GetEncChnListRequest();
        request.setSsid(ssid);

        GetEncChnListResponse respose = (GetEncChnListResponse) this.sendRequest(request);
        return respose.getEncChnList();
    }

    public static synchronized KM getKm() {
        if (km == null) {
            km = KM.getInstance();
            km.start();
        }
        return km;
    }

    public static void main(String[] args) {
        try {
            SVRClient client = getKm().getSVRClient();
            SVR svr = new SVR();
            svr.setIp("172.16.237.101");
            svr.setUsername("admin");
            svr.setPassword("admin123");
            svr.setPort(1730);
            int[] chnid = new int[]{1, 17, 3, 2, 1, 18, 4, 34};
            int[] pictype = new int[]{1, 0, 0, 0, 1, 0, 0, 0};
            int ret = client.setSvrComposePic(svr, 9, 2, 9, 8, chnid, pictype);
            System.out.println(ret);
        } catch (KMException e) {
            e.printStackTrace();
        }
    }
}
