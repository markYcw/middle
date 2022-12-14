package com.kedacom.middleware.vrs.domain;

import lombok.Data;

/**
 * 终端相关信息
 * @author ycw
 * @date 2021/5/13 15:20
 */
@Data
public class MtInfo {
    //终端名称
    private String name;

    //终端ip
    private String ip;

    //用户名
    private String username;

    //密码
    private String passwd;

    //终端类型
    private String typeinfo;

    //终端类型
    //1：4.7终端
    //2: 5.0终端
    //3：4.7MCU
    //4: 5.0MCU
    private int type;

    //("认证软件的Key  备：仅终端类型为4有效 5.0MCU平台获取token用")
    private String key;

    //("认证软件的Key的值value 备： 仅终端类型为4有效 5.0MCU平台获取token用")
    private String secret;

    //中间件IP
    private String midip;

}
