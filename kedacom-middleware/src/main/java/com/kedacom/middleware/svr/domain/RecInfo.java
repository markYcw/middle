package com.kedacom.middleware.svr.domain;

import lombok.Data;
/**
 * 录像信息
 * @author ycw
 * @date 2021/4/19 13:16
 */
@Data
public class RecInfo {
    /**
     * 录像记录ID，唯一
     */
    private int id;

    /**
     * 录像记录数据大小，单位MB
     */
    private int size;

    /**
     * 录像码流分辨率
     * 1:D1,0:other
     */
    private int resolution;

    /**
     * 通道信息
     */
    private Chn chn;

    /**
     * 开始时间
     */
    private String starttime;

    /**
     * 结束时间
     */
    private String endtime;

    /**
     * MD5校验码
     */
    private String md5;
}
