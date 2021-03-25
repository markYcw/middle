package com.kedacom.middleware.mcu.domain;

import lombok.Data;

/**
 * 轮询终端信息
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class Members {

    /**
     * 轮询终端 最大字符长度：48个字节
     */
    private String mt_id;
}
