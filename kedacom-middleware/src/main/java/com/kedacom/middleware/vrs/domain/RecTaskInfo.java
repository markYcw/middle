package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * 终端相关信息
 * @author ycw
 * @date 2021/5/18 14:14
 */
@Data
public class RecTaskInfo {

    //录像任务ID
    private int taskid;

    //录像名称
    private String taskname;

    //开始时间
    private String starttime;

    //结束时间
    private String endtime;

    //任务大小
    private int size;

    //是否发布
    private int ispublish;

    //备注
    private String remark;


}
