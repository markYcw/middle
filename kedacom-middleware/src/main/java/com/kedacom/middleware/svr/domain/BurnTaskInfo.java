package com.kedacom.middleware.svr.domain;

import lombok.Data;

/**
 * 刻录任务信息
 * @author ycw
 * @date 2021/4/25 15:49
 */
@Data
public class BurnTaskInfo {
    /**
     * 刻录任务ID
     */
    private Integer burntaskid;
    /**
     * 刻录任务数据库ID
     */
    private Integer burntaskdbid;
    /**
     * 开始时间
     */
    private String starttime;
    /**
     * 持续时间
     */
    private String durationtime;
}
