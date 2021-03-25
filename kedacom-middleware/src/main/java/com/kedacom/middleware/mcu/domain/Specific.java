package com.kedacom.middleware.mcu.domain;

import lombok.Data;

/**
 * 选看信息 仅在mode为1有效
 * @author ycw
 * @Date 2021/3/24
 */
@Data
public class Specific {

    /**
     *选看类型
     * 1-指定；
     * 2-发言人跟随；
     * 3-主席跟随；
     * 4-会议轮询跟随；
     * 6-选看画面合成；
     * 10-选看双流；
     */
    private String member_type;

    /**
     * 选看终端id，仅member_type为 1-指定时生效 最大字符长度：48个字节
     */
    private String mt_id;

    /**
     * 选看画面合成id，仅membertype为 6-选看画面合成时生效 最大字符长度：48个字节
     */
    private String vmp_id;
}
