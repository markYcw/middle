package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * DVD信息
 * @author ycw
 * @date 2021/5/11 15:25
 */
@Data
public class DvdStatus {

    //刻录任务名称
    private String burntaskname;

    //Dvd状态，见下面DVD状态取值
    private int dvdstatus;

    //光盘总容量（单位M）
    private int totalcapacity;

    //光盘剩余容量（单位M）
    private int remaincapacity;

    //刻录进度
    private int burnprocess;

    // dvd工作状态 1：空闲 2：光盘刻录 3：光盘读取
    private int dvdworkstatus;

}
