package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * 录像室状态信息
 * @author ycw
 * @date 2021/5/24 10:15
 */
@Data
public class RoomStatus {

    //录像室ID
    private int roomid;

    //录像室是否被使用 0：空闲 1：使用中
    private int isuse;

    //获取码流方式 0:中间件1:MT
    private int getstreammode;

    //获取码流状态0：未获取1：获取中 2：获取正常3：获取异常
    private int getstreamstatus;

    //是否转码0：未转码1：转码
    private int istranscoding;

    //录像状态0：未录像1：等待录像2：录像中3 : 等待停止录像4 : 暂停录像
    private int recstatus;

    //录像任务名称
    private String rectaskname;

    //录像时长
    private int rectime;

    //是否开启实时刻录0：未开启1：开启
    private int isburn;

    //0：双光盘刻录1：循环刻录
    private int burnmode;

    //0：未刻录1：刻录中2：刻录完成
    private int burnstatus;

    //本地终端名称
    private String localmtname;

    //远端终端名称
    private String remotemtname;

    private int threestreamstatus;

    private int lasterrno;

    private int conferencestatus;


}
