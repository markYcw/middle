package com.kedacom.middleware.cu.domain;

import lombok.Data;

/**
 * 轮询集
 *
 * @author ycw
 *
 */
@Data
public class Polls {
    /**
     * 编码器
     */
    private Encchnl encchnl;
    /**
     * 选看持续时间（秒）
     */
    private int dtn;
}
