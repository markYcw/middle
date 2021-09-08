package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/9/8 11:11
 * @description录像文件信息
 */
@Data
public class RecFile {

    //录像任务id
    private int taskid;

    //录像文件id
    private int fileid;

    //文件路径
    private String path;

    //开始时间
    private String starttime;

    //结束时间
    private String endtime;

    //文件大小
    private int size;

}
