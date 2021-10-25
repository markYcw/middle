package com.kedacom.middleware.svr.domain;

import lombok.Data;

/**
 * @author ycw
 * @version v1.0
 * @date 2021/10/25 13:52
 * @description 远程点设备
 */
@Data
public class SvrDevList {

    /**
     * 远程点名称
     */
    private String name;

    /**
     * 远程点url
     */
    private String url;

}
