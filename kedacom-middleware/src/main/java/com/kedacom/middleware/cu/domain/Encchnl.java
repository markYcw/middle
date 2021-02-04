package com.kedacom.middleware.cu.domain;

import lombok.Data;



/**
 * 编码器
 *
 * @author ycw
 *
 */
@Data
public class Encchnl {
    /**
     * 解码器设备id
     */
    private String puid;
    /**
     * 解码器输出id
     */
    private int chnid;
}
