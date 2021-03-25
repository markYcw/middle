package com.kedacom.middleware.mcu.domain;

import lombok.Data;

/**
 * 电视墙信息
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class HduInfo {

    /**
     *电视墙id
     */
    private String hdu_id;

    /**
     *电视墙模式：
     *1-选看；
     *2-四分屏(仅传统会议有效)；
     *3-单通道轮询；
     */
    private int mode;

    /**
     *选看信息
     * 仅在mode为1有效
     */
    private Specific specific;

    /**
     * 轮询信息
     * 仅在mode为3有效
     */
    private Poll poll;

    /**
     * 分屏信息
     * 仅在mode为2有效
     */
    private Spilt spilt;




}
