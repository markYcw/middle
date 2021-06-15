package com.kedacom.middleware.rk100.domain;

/**
 * @ClassName RK
 * @Description RK100服务器参数信息
 * @Author zlf
 * @Date 2021/6/10 9:51
 */
public class RK {

    /**
     * RK本地标识。比如：RK信息在本地数据库的数据ID
     */
    private String id;

    /**
     * RK会话标识。登录RK后，中间件返回的服务标识。
     */
    private int ssid;

    /**
     * rabbitMQ地址
     */
    private String ip;

    /**
     * rabbitMQ地址连接端口
     */
    private int port;

    public void update(RK rk) {
        this.setId(rk.getId());
        this.setIp(rk.getIp());
        this.setPort(rk.getPort());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSsid() {
        return ssid;
    }

    public void setSsid(int ssid) {
        this.ssid = ssid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
