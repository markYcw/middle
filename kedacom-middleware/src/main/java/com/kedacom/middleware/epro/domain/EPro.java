package com.kedacom.middleware.epro.domain;

import lombok.Data;

/**
 * @ClassName EPro
 * @Description E10Pro服务器参数信息
 * @Author ycw
 * @Date 2021/06/30 13:16
 */
@Data
public class EPro {

    /**
     * EPro本地标识。比如：RK信息在本地数据库的数据ID
     */
    private String id;

    /**
     * EPro会话标识。登录RK后，中间件返回的服务标识。
     */
    private int ssid;

    /**
     * EPro Ip地址
     */
    private String ip;

    /**
     * EPro地址连接端口
     */
    private int port;

    public void update(EPro ePro) {
        this.setId(ePro.getId());
        this.setIp(ePro.getIp());
        this.setPort(ePro.getPort());
    }

}
