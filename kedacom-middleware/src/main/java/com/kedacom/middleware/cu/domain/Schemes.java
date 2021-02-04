package com.kedacom.middleware.cu.domain;

import lombok.Data;

import java.util.List;

/**
 *
 *预案
 * @author ycw
 *
 */
@Data
public class Schemes {
    /**
     * 电视机编号
     */
    private int tvid;
    /**
     * 解码器puid
     */
    private String decpuid;
    /**
     * 画面风格
     * （取值：风格）(1:1,2:2,3:3,4:4,5:6,6:8,7:9, 8:16,9:25,10:36)
     */
    private int stype;
    /**
     *解码集
     */
    private List<Divs> divs;
}
