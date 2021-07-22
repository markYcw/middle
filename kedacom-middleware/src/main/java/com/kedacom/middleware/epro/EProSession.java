package com.kedacom.middleware.epro;

import com.kedacom.middleware.epro.domain.EPro;

/**
 * @ClassName EProSession
 * @Description TODO
 * @Author ycw
 * @Date 2021/06/30 14:12
 */
public class EProSession {

    /*
     * 会话标识
     */
    private int ssid;

    /*
    E10Pro信息
     */
    private EPro ePro;

    /*
    连接状态
     */
    private EProSessionStatus status;

    /*
    会话创建时间，单位：毫秒
     */
    private long createTime;

    /*
    最后访问时间，单位：毫秒
     */
    private long lastTime;

    public EProSession() {
        this.status = EProSessionStatus.NONE;
        this.createTime = System.currentTimeMillis();
        this.lastTime = createTime;
    }

    public boolean isLogin() {
        return status == EProSessionStatus.CONNECTED;
    }

    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }


    public EPro getEPro() {
        return ePro;
    }

    public void setEPro(EPro ePro) {
        this.ePro = ePro;
    }


    public EProSessionStatus getStatus() {
        return status;
    }

    public void setStatus(EProSessionStatus status) {
        this.status = status;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * 刷新会话的最后使用时间
     */
    public void refreshTime() {
        this.lastTime = System.currentTimeMillis();
    }

}
