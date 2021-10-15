package com.kedacom.middleware.svr.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName SvrEncChnList
 * @Description 编码通道列表
 * @Author zlf
 * @Date 2021/5/31 15:12
 */
@Data
public class SvrChnList implements Serializable {

    /*
   远程点通道id
     */
    private int ChnId;

    /*
    远程点通道别名
     */
    private int ChnAlias;

    /*
    远程点是否在线
     */
    private int IsOnline;


}
