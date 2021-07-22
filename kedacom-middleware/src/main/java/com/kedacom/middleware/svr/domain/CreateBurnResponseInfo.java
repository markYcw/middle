package com.kedacom.middleware.svr.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CreateBurnResponse
 * @Description 创建刻录任务通知信息
 * @Author zlf
 * @Date 2021/6/1 13:42
 */
@Data
@Builder
public class CreateBurnResponseInfo implements Serializable {

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
