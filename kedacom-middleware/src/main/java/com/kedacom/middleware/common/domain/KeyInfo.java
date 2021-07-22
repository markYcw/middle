package com.kedacom.middleware.common.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * keys集合信息
 */
@Data
public class KeyInfo {

    /**
     * 0正常;
     * 1没有key,没有找到与type匹配的key;
     * 2key错误；
     * 3系统错误；
     */
    private int keystate;

    /**
     * 是否是usbkey
     */
    private boolean usbkey;

    private Map<String, String> attributes = new HashMap<String, String>();
}
