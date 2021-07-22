package com.kedacom.middleware.rk100;

import com.kedacom.middleware.rk100.domain.RK;

/**
 * @ClassName RkSession
 * @Description TODO
 * @Author zlf
 * @Date 2021/6/10 9:45
 */
public class RkSession {

    /*
     * 会话标识
     */
    private int ssid;

    /*
    终端信息
     */
    private RK Rk;

    /*
    连接状态
     */
    private RkSessionStatus status;

    /*
    会话创建时间，单位：毫秒
     */
    private long createTime;

    /*
    最后访问时间，单位：毫秒
     */
    private long lastTime;

    public RkSession() {
        this.status = RkSessionStatus.NONE;
        this.createTime = System.currentTimeMillis();
        this.lastTime = createTime;
    }

    public boolean isLogin() {
        return status == RkSessionStatus.CONNECTED;
    }

    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }


    public RK getRk() {
        return Rk;
    }

    public void setRk(RK Rk) {
        this.Rk = Rk;
    }


    public RkSessionStatus getStatus() {
        return status;
    }

    public void setStatus(RkSessionStatus status) {
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
