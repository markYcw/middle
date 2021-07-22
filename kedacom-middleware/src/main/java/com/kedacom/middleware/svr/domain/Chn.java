package com.kedacom.middleware.svr.domain;

import lombok.Data;
/**
 * 通道信息
 * @author DengJie
 * @date 2021/4/19 13:16
 */
@Data
public class Chn {
    /**
     * 录像来源设备类型
     */
    private int devtype;

    /**
     * 录像来源设备ID
     */
    private int devid;

    /**
     * 录像通道id，特殊0表示合成通道
     */
    private int chnid;

    /**
     * 刻录任务id
     */
    private int taskid;
}
