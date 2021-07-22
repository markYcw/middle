package com.kedacom.middleware.vrs.domain;

import lombok.Data;

import java.util.List;

/**
 * 系统状态信息
 * @author ycw
 * @date 2021/5/24 16:12
 */
@Data
public class SysStatus {

    /**
     * 刻录工作模式
     * 0：实时刻录（录像并刻录）
     * 1：补刻
     * 2：只录像不刻录
     */
    private int burnworkmode;

    private List<DvdStatus> dvdStatuses;
}
