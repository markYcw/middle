package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * 放像相关信息
 * @author ycw
 * @date 2021/5/11 15:20
 */
@Data
public class DstAddr {
    /**
     * 视频信息
     */
    private Video video;

    /**
     * 音频信息
     */
    private Audio audio;
}
