package com.kedacom.middleware.svr.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CreateBurnResponse
 * @Description 创建刻录任务通知信息
 * @Author ycw
 * @Date 2021/12/1 13:42
 */
@Data
@Builder
public class CreateBurnResponseInfo implements Serializable {

    /**
     * 命令流水号
     */
    private Integer ssno;

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
