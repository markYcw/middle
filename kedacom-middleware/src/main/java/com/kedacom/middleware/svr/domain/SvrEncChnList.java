package com.kedacom.middleware.svr.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SvrEncChnList
 * @Description 编码通道列表
 * @Author zlf
 * @Date 2021/5/31 15:12
 */
@Data
public class SvrEncChnList implements Serializable {

    /*
    通道ID
     */
    private int chnId;

    /*
    设备ID
     */
    private int devId;

    /*
    设备类型
     */
    private int devType;

    /*
    通道是否有效
     */
    private int isValid;

    /*
    是否在线
     */
    private int isOnline;

    /*
    是否正在录像
     */
    private int isRec;

    /*
    是否支持辅流
     */
    private int isSupSecStream;

    /*
    是否启用了sdi
     */
    private int isStartUpSdi;

    /*
    通道别名
     */
    private String chnAlias;

}
