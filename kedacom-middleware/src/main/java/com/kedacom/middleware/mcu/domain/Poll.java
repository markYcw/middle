package com.kedacom.middleware.mcu.domain;

import lombok.Data;

import java.util.List;

/**
 * 轮询信息
 * 仅在mode为3有效
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class Poll {

    /**
     * 轮询次数
     */
    private String num;

    /**
     * 轮询方式
     * 1-仅图像；
     * 3-音视频轮询；
     */
    private String mode;

    /**
     * 轮询间隔（秒）
     */
    private String keep_time;

    /**
     * 轮询终端
     */
    private List<Members> members;

}
