package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * VRS录像信息
 * @author ycw
 * @date 2021/6/21 15:20
 */
@Data
public class RecLiveInfo {

    /**
     * 录像名称
     */
    private String name;

    /**
     * http的播放链接
     */
    private String url;

}
