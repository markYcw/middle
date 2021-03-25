package com.kedacom.middleware.mcu.domain;

import lombok.Data;
/**
 * 电视墙信息
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class Hdu {

    /**
     * 电视墙Id
     */
    private String hdu_id;

    /**
     *电视墙别名
     */
    private String hdu_name;

    /**
     *是否在线
     */
    private int occupy;

    /**
     *是否被占用
     */
    private int online;

}
