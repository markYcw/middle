package com.kedacom.middleware.mcu.domain;

import lombok.Data;

/**
 * 四分屏终端/通道信息
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class MemberInfo {

    /**
     * 四分屏通道号, 从0开始到3
     */
    private int chn_idx;

    /**
     * 分屏内终端 最大字符长度：48个字节
     */
    private String mt_id;
}
