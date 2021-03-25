package com.kedacom.middleware.mcu.domain;

import lombok.Data;

import java.util.List;


/**
 * 分屏信息
 * 仅在mode为2有效
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class Spilt {

    /**
     * 四分屏终端/通道信息
     */
    private List<MemberInfo> members;
}
